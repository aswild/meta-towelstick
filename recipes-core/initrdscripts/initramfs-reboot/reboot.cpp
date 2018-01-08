/*
 * Copyright 2018 Allen Wild <allenwild93@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 *
 * Program to shutdown/poweroff/halt/reboot from an initramfs.  First tries to
 * unmount all filesystems, or remount as read-only
 */

#include <iostream>
#include <string>
#include <list>

#include <cerrno>
#include <cstring>
#include <mntent.h>
#include <unistd.h>
#include <linux/reboot.h>
#include <sys/reboot.h>

using std::cout;
using std::endl;
using std::string;
using std::list;

#ifndef TEST_BUILD
#include <sys/mount.h>
#define _reboot reboot
#else
/* phony test functions */
#define MS_REMOUNT 1
#define MS_RDONLY  2
int mount(const char *source, const char *target,
          const char *filesystemtype, unsigned long mountflags,
          const void *data)
{
    cout << "PHONY Mount " << target << ", flags " << mountflags << endl;
    return 0;
}
int umount(const char *target)
{
#if 0
    if (!strcmp(target, "/boot"))
        return 1;
#endif
    cout << "PHONY Unmount " << target << endl;
    return 0;
}

int _reboot(int cmd)
{
    cout << "PHONY Reboot: ";
    switch (cmd)
    {
        case LINUX_REBOOT_CMD_HALT:
            cout << "Halt";
            break;
        case LINUX_REBOOT_CMD_POWER_OFF:
            cout << "Power Off";
            break;
        case LINUX_REBOOT_CMD_RESTART:
            cout << "Restart";
            break;
        default:
            cout << "Unknown command";
            break;
    }
    cout << endl;
    return 0;
}

#endif // TEST_BUILD

/*
 * get all mounted filesystems that match /dev/sd*
 */
static void populate_mountdevs(list<string> &devs)
{
    struct mntent *mnt;
    FILE *fp;

    fp = setmntent(_PATH_MOUNTED, "r");
    if (!fp)
        return;

    while ((mnt = getmntent(fp)) != NULL)
    {
        if (string(mnt->mnt_fsname).find("/dev/sd", 0) == 0)
            devs.push_back(mnt->mnt_dir);
    }

    // process unmounting in reverse
    devs.reverse();
}

static int unmount_all(list<string> &devs)
{
    bool err = 0;
    list<string> failed;

    for (auto dev : devs)
    {
        if (umount(dev.c_str()) == 0)
        {
            cout << "Unmounted " << dev << endl;
        }
        else
        {
            cout << "Failed to unmount " << dev << ": " << strerror(errno) << endl;
            failed.push_back(dev);
            err = -1;
        }
    }

    if (failed.size())
    {
        cout << "Couldn't unmount some filesystems, trying to remount as read-only..." << endl;
        for (auto dev : failed)
        {
            if (mount(NULL, dev.c_str(), NULL, MS_REMOUNT|MS_RDONLY, NULL) == 0)
            {
                cout << "Remounted " << dev << " as read-only" << endl;
            }
            else
            {
                cout << "Failed to remount " << dev << " as read-only: " << strerror(errno) << endl;
                err = -1;
            }
        }
    }

    return err;
}

int main(int argc, char *argv[])
{
    list<string> mountdevs;
    int reboot_cmd = 0;

    string name;
    auto a0 = string(argv[0]);
    auto slash = a0.rfind('/');
    if (slash == string::npos)
        name = a0;
    else
        name = a0.substr(slash+1);

    if (name == "reboot")
        reboot_cmd = LINUX_REBOOT_CMD_RESTART;
    else if (name == "poweroff")
        reboot_cmd = LINUX_REBOOT_CMD_POWER_OFF;
    else if (name == "halt")
        reboot_cmd = LINUX_REBOOT_CMD_HALT;
    else
    {
        cout << "Invoked with unknown name '" << name << "'" << endl;
        return 1;
    }

    populate_mountdevs(mountdevs);
    if (unmount_all(mountdevs))
    {
        cout << "Errors occured when unmounting. Continuing in 5 seconds..." << endl;
        sleep(5);
    }

    sync();
    _reboot(reboot_cmd);

    // if we get here, the reboot failed
    cout << "Reboot failed: " << strerror(errno) << endl;

    return 0;
}
