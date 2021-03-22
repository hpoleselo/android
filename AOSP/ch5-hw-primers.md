#

eMMC = embedded Multimedia Card

mmc block device #0, partition #2. On a Pi that's the SD card that's in the SD card slot.
For a Raspbian image mmcblk0p1 is the first partition (that's the FAT partition with the boot files)
And mmcblk0p2 is the ext4 partition with the root file system



Separation in many folders, why? Security for future updates...



YAFFS2 Yet Another Flash File System, ext4 é multicore/multithreaded que advém do eMMC.



![image-20210309184021860](/home/henrivis/.config/Typora/typora-user-images/image-20210309184021860.png)

![image-20210309184030339](/home/henrivis/.config/Typora/typora-user-images/image-20210309184030339.png)

![image-20210309184046794](/home/henrivis/.config/Typora/typora-user-images/image-20210309184046794.png)



![image-20210309184055328](/home/henrivis/.config/Typora/typora-user-images/image-20210309184055328.png)