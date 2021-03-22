# Hands-On on the Built AOSP

This presumes you have the AOSP built on your Ubuntu machine. This document is intended to give an overview/kickstart regarding the basic tools to start debugging/customizing your own device in AOSP.

**Note: The dollar sign that precedes the commands are just a common notation used in the community to refer as a shell command (console command).**

## Important Shell Commands

Before starting with the hands-on with the AOSP is good to know some basic terminal commands.

Content inside a file without opening any editor:

` $ cat FILE`

Print working directory:

`$ pwd`

Shows the folder structure and files in a more organized way:

`$ tree`

Searches for names inside files AND folders:

`$ grep -rw "WHATYOUARELOOKINGFOR"`

Setting/unsetting environment variables (important for build env. variables like OUT_DIR etc.)

`$ export ENVIRONMENTVARIABLE`

`$ unset ENVIRONTMENTVARIABLE`

List all environment variables and filter them out using grep:

`$ printenv | grep YOURFILTER`

## Building the AOSP (and running it)

`$ source envsetup.sh`

In order to launch the emulator one has to lunch the wished build:

`$ lunch`

`$ emulator &`

Now you should be seeing the emulator running your target.

Open up another terminal and do the same procedure as above (envtsetup, lunch and emulator) and run the following command to access your target:

`$ adb shell`

The shell user should change to the device's user, in my case the build is named generic_x86_64:

`generic_x86_64:/`

Now we can input any command we want inside our target.

`$ uname -a`
`Linux ariel-PC 5.8.0-45-generic #51~20.04.1-Ubuntu SMP Tue Feb 23 13:46:31 UTC 2021 x86_64 x86_64 x86_64 GNU/Linux`

`$ cat /proc/cpuinfo`

```
vendor_id	: AuthenticAMD
cpu family	: 6
model		: 6
model name	: Android virtual processor
stepping	: 3
microcode	: 0x1000065
cpu MHz		: 3393.275
cache size	: 512 KB
physical id	: 0
siblings	: 2
core id		: 0
cpu cores	: 2
(..)
```

And if we list the files and directories we can see that we're in the root of the target:

`$ ls`

`ls: ./init: Permission denied`
`ls: ./init.rc: Permission denied`
`ls: ./init.zygote64_32.rc: Permission denied`
`ls: ./init.usb.rc: Permission denied`
`ls: ./init.zygote32.rc: Permission denied`
`ls: ./init.usb.configfs.rc: Permission denied`
`ls: ./init.car.rc: Permission denied`
`ls: ./init.zygote32_64.rc: Permission denied`
`ls: ./init.bootstat.rc: Permission denied`
`ls: ./init.environ.rc: Permission denied`
`ls: ./ueventd.rc: Permission denied`
`ls: ./metadata: Permission denied`
`acct bin        cache   config data          default.prop etc        mnt oem  product          res  sdcard  sys    vendor` 
`apex bugreports charger d      debug_ramdisk dev          lost+found odm proc product_services sbin storage system` 

Acessing the /data directory, which contain all the installed apps from the user and data from the installed apps:

`$ cd /data`

This directory is empty, since no user has really installed anything.

Let's try to put some files manually on the root of the system. within our host machine. Open up another terminal try to push any file to the /data directory:

`$ adb push TestFile.txt /`
`adb: error: failed to copy 'TestFile.txt' to '/TestFile.txt': remote couldn't create file: Read-only file system`

If you try to push any file/directory into any other directory than /data, an error will be thrown as we're in the target's root directory and it's mounted as read-only (this can be proved by looking at the /system/init/...).

Now let's try to push the file on the proper place, i.e: data, which is a read-write mounted directory:

`$ adb push TestFile.txt /data`
`adb: error: failed to copy 'TestFile.txt' to '/data/TestFile.txt': remote couldn't create file: Permission denied`
`TestFile.txt: 0 files pushed. 0.0 MB/s (6 bytes in 0.001s)`

Didn't work. That's because adb deamon (adbd) isn't being run as root privileges, what if we try to sudo in the adb shell?

`$ sudo`
`/system/bin/sh: sudo: inaccessible or not found`

What about root?

`$ root`
`/system/bin/sh: root: inaccessible or not found`

See the error that both commands threw: the shell tries to invoke the sudo and root commands from the shell (like any other shell command: ls, cat..), but they do not exist. And yes, sudo and root are actually binaries in Linux, they don't come default. 

To solve this, go to the terminal that you're pushing the files, do a:

`$ adb root`
`restarting adbd as root`

It will end the shell session opened in one of the terminals and restart the adbd as root. 

Now if we want to try to push:

`$ adb push TestFile.txt /data`
`TestFile.txt: 1 file pushed. 0.0 MB/s (6 bytes in 0.008s)`

It works! Yurrah!

And accessing the data directory: (note that it shows way more files in comparison to when we were using adb as non-root, and it doesn't complain about listing files that have special permissions [such as zygote.rc, init.rc...])

`generic_x86_64:/data # ls`
`TestFile.txt app-ephemeral cache        local.prop misc_de     property                  system      user_de   
adb          app-lib       dalvik-cache lost+found nfc         resource-cache            system_ce   vendor    
anr          app-private   data         media      ota         rollback                  system_de   vendor_ce` 
`apex         app-staging   drm          mediadrm   ota_package rollback-observer         tombstones  vendor_de` 
`app          backup        gsi          misc       per_boot    server_configurable_flags unencrypted` 
`app-asec     bootchart     local        misc_ce    preloads    ss                        user`

See that our TestFile.txt is there, on the top!



What we have to do is to root the device, on the terminal that 



Any corrections or contributions, contact me: hpolese1@ford.com :-)

