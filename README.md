# Battle of Balls

![game](https://github.com/yixiaowang2001/COMP-128_FP/blob/main/res/Pic1.png)

待完成任务：
+ AI ball
+ 游戏逻辑
+ 球速函数（调整球速）
+ 球和boundary的互动（球不能出bound）

待解决问题：
+ Back To Menu 有时候会报错（`Exception in thread "AWT-EventQueue-0" java.util.NoSuchElementException: The object to remove is not part of this graphics group. Either it is already removed, or it was never originally added.`）
+ `offsetX`和`offsetY`的逻辑有点问题（锐评：没啥用）

待讨论：
+ Resize变大的速率（目前是constant，是否需要改成非线性）