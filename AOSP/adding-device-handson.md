# Adding a device to AOSP

What is presented in the book Embedded Android is quite outdated, one can realize that because vendorsetup.sh is deprecated. Here we see as well about the "new" build system that Android uses, which is Soong. On the book it isn't

## Basics concepts before adding a new device in AOSP

Every system that uses Android has a CPU and each CPU has an architecture. Cellphones (hence Android) are famous for having ARM architecture, but we can find Android based on x86 and MIPS architecture as well.

Some apps from Android make use of the NDK in order to create C/C++ links (say you want to implement something more low-level so you have to code in C (instead of staying on the Java programming layer)). The thing about C/C++ is that it has to be compiled to a certain architecture, so it's important to us to specify the architecture we're working, mainly when we build our system. So under the /ndk directory usually you have the precompiled libraries under different targets:

`$ pwd`

`home/ariel/AOSP/android/prebuilts/ndk`

Now using tree to better understand what's inside ndk's directory (limiting the listing to 2 layers so we don't verbose our Shell with too many files):

```$ tree -L 2
├── Android.bp
├── Android.mk
├── android_native_app_glue.bp
├── CleanSpec.mk
├── cpufeatures.bp
├── current -> r18
├── gen_blueprints.py
├── NOTICE
├── pylintrc
├── r16
│   ├── platforms
│   ├── source.properties
│   └── sources
├── r18
│   ├── platforms
│   ├── source.properties
│   └── sources
├── stl.bp
└── update.py

7 directories, 12 files
```

When adding a new device one thing that you may face is building errors due dependencies, one of them is the llvm-libc++ standard library used by Android, located under:

`$ pwd`

/home/ariel/AOSP/android/prebuilts/ndk/current/sources/cxx-stl/llvm-libc++/lib`

See that it has different target architectures since Android can be found in different systems besides arm based CPUs (as we spoken on the beginning of this section):

```$ tree -L 2
├── arm64-v8a
├── armeabi-v7a
├── x86
└── x86_64
```


## Adding a new device

The basic folder/file structure to add a new device using AOSP is: (say )

## Android Build System

Before Android 7.0, GNU Make was used to build the Android system. Soong comes to replace it (though make is still used, you see a make directory inside /build, you kinda have a mix of both, but the main build system is Soong). 

The file extension used by Soong is .bp (blueprints), instead of .mk files. Android.bp may be the most famous one (which would be the Android.mk). Blueprints don't have flow control/complexity built-in like mk files, instead, when something more complex is needed they point .go file. You can check some of those files by doing:

$ tree /build/blueprints

Looking at the envsetup.sh gives us a good notion of what we're actually doing:

```
function m()
(
    _trigger_build "all-modules" "$@"
)
```

The function m calls the trigger_build function, but what is the trigger_build function?

```
function _trigger_build()
(
    local -r bc="$1"; shift
    if T="$(gettop)"; then
      _wrap_build "$T/build/soong/soong_ui.bash" --build-mode --${bc} --dir="$(pwd)" "$@"
    else
      echo "Couldn't locate the top of the tree. Try setting TOP."
    fi
)
```

Basically it runs the soong build system.



## Debug Notes

kati generates ninja files, which generates the commands for building the system.image, for instance: the build.ninja file generates several shell commands (the file is about 1 million lines long). While debugging a problem, we realized that inside the build.ninja there were several shell commands in sequence (about the image properties: ext4, partition size...) which then would be retrieved by build_image.py via argv[0] and then built.



## Resources/Links

https://programmersought.com/article/28501922508/

https://stackoverflow.com/questions/50264740/how-the-soong-android-bp-build-works

