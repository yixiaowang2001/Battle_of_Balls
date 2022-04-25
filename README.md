# Battle of Balls

![game](https://github.com/yixiaowang2001/COMP-128_FP/blob/main/res/Pic1.png)

待完成任务：
+ AI ball
+ 游戏逻辑
+ Ball stroke
+ 球速函数（调整球速）
+ 球和boundary的互动（球不能出bound）

待解决问题：
+ Back To Menu 有时候会报错（Concurrent Exception？）
+ `controlNum()`会把球创建在外面：需要动态更新CanvasWindow的位置

待讨论：
+ Resize变大的速率（目前是constant，是否需要改成非线性）