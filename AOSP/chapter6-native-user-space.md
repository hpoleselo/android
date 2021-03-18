# Chapter 6: Native User-Space

When referring to target means the built AOSP for the target

When referring to source means the AOSP tree pulled from the git repository.

TODO:

- Try to use basic commands from the table given to customize a init file (create a folder and so on)
- ps aux for the init process while running the emulator
- Verify setprops getprops
- Tirar a imagem .rle e ver se o display text customizado mudará
- Mexer no Launcher's source para customizar a starting screen
- Setar variável TARGET_BUILD_VAR para rodar o adbd como root por padrão no emulator 
- Buildar AOSP com Busybox

## Android Bridge Debugger: adb 

The main functionality from Android is adb and adb is most used to shell into the target device so we can have control or debug the device. We can use adb to shell to more than one device, here are some basic commands from adb:

List all devices that are connected to our host, we expect to see just the emulator on port 5554:

$ adb devices

Logcat is also known for people who develop apps for Android, we can combine logcat with adb and search for a specific service:

`$ adb -d logcat -b radio`

`$ adb -d status-window`

Pushing and pulling files from the device:

`$ adb push` 

adb pull /system/media/bootanimation.zip
/system/media/bootanimation.zip: 1 fil.... 167.6 MB/s (2052281 bytes in 0.012s)

`$ adb pull`

`$ adb shell getprop`

Show types instead of values:

`$ adb shell getprop -T`

All the commands for now were given outside the target, what if we want to access it? Note that everything that precedes shell can be run outside from the target Now, to properly enter into the target device and navigate through the filesystem:

`$ adb shell`

List all processes and filter which one you want:

`$ ps -A | grep FILTER-BY-WORD`

By the default adb is started as non-root, so if you want to remount any partition, say you want to 

`$ adb root`

`$ adb remount` 

Notes: adbd (adb daemon) always run as root on the emulator, in other cases (non-emulation), we have to set the variable TARGET_BUILD_VAR. Note that are services that you can check regarding the adb running as shell or not, those being: ro.secure and ro.debuggable [set at build time] and `service.adb.root` [at runtime]

COMENTAR SOBRE OS RO (read-only) que na verdade aparecem no init e são relacionados ao getprop

## Android Command Line

adb works well when we have our AOSP built and fully working. What when we're developing this low level part, how can we interact with the system without having the native user space completely working, i.e without any UI/adb? The answer is the Android Command Line, which is a serial console for Android. The commands are based on shell as well, but they are scarce, expect to be more basic than Toolbox, for example: ACL doesn't have tab completion, color coding for files/directories, back then, before Android 4.0 the `ls` didn't even ordered in alphabetical order!

But since Android 4.0, Mksh (MirBSD Korn Shell) has tab completion and lists in alphabetical order among other important shell utilites.

The location of the Mksh nowadays can be found at (source):

`/system/bin/mksh`

To check its documentation:

`$ man /external/mksh/src/mksh.1`

Busybox is well known for people who work 

In the book the author even refers ACL commands by # instead of $ because they're in Native User-Space. 

low level services nao podem ser listados no Android, entao

`# start service`

`# stop service`

`# stop zygote`

To list all running processes (much like top or htop):

`# schedtop`

## Init

The kernel loads the ramdisk.img, which loads the init process, which mounts the /system and /data directories

Usually on Linux systems, despite init being the first process to run (hence its PID 1) usually the init dies after doing the boot process. In contrast, Android's init works a little bit different: if init dies, the kernel would panic, so after init has mounted the /system, /dev and /proc directories, it runs an infinite loop so all the child processes/native daemons can be run. While running the emulator we can check that init is still running and its PID:

`$ ps -A | grep init`

In order to tweak with the init portion, we can go to our source OR TARGET? root's directory and find two files: init.rc and init.DEVICE_NAME.rc. Important to say that when we want to customize the init process the best way to do it is by tweaking the init.DEVICE_NAME.rc and not init.rc since init.rc has fundamental procedures in order to AOSP to boot, so we end up working with the device-specific rc file.

Note that init doesn't accept any shell command, for the contrary, it's very restrictive. Instead, its semantics is based on actions and services (these services has nothing to do with the service component used by app devs.), but the commands from init resemble a lot to the command-line Toolbox (so in the end, resembles to the unix-like commands), a list of them taken from Embedded Android book:

FOTO

 The variable DEVICE_NAME is extracted from /proc/cpuinfo, in Android's emulator it's called goldfish.

`/system/core/rootdir/etc/init.goldfish.rc`

#### Init: ueventd

Ueventd manages all the devices nodes peripherals, that's why it's initialized along with init. The /dev directory only makes sense with ueventd

#### Init: Global Properties

Something very useful that i haven't payed attention while reading the book. Basically variables that are

When choosing compilation variants before building, some global properties are set, such: `ro.secure`, `ro.debuggable`, if adb is activated or not... For example, for user build, the `ro.debuggable=0`, for eng environment `ro.debuggable=1`.

O setprop consegue setar apenas alguns properties, mas este não pode ser chamado dentro do SDK normal, a API do AOSP não dá suporte pra isso

#### Init: Bootlogo

Init boot logo, if there's no image a text is displayed. And then boot animation, then home screen.

From what it looks like the bootlogo doesn't exist anymore. the rgb2565 for converting the raw image to .rle format isn't in the AOSP tree anymore. And while searching for initiallogo.rle on the tree you will find nothing. Despite that, taking a glimpse at `/system/core/init/init.cpp`, the `console_init_action()` function basically mounts the devices.

no full_device.mk adicionar PRODUCT_COPY_FILE += \

device/ford/phoenix/swteamlogo.rle:root/initlogo.rle

Very Useful Resources:

- [How the boot process works](https://www.programmersought.com/article/86081638651/)
- [Remounting /system as read-write](https://www.programmersought.com/article/17402573535/): 
- https://stackoverflow.com/questions/45947670/how-disable-dm-verity-on-android-devices-without-using-adb
- https://stackoverflow.com/questions/28009716/how-can-i-remount-my-android-system-as-read-write-in-a-bash-script-using-adb

