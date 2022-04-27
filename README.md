# Battle of Balls

![game](https://github.com/yixiaowang2001/COMP-128_FP/blob/main/res/Pic1.png)

待完成任务：
+ 重量/半径/得分的显示
+ 记分板
+ 球的名字显示
+ AI Ball
  + AI Ball吃AI Ball
  + AI Ball的boundary
  + AI Ball吃Circle

待解决问题：

待讨论：
+ 是否要用animate
+ Player Ball吃AI Ball的增长速率

Presentation
+ Classes created
  + 用思维导图来解释class之间的内容和关系
+ Data structure
  + ArrayList
    + 作用：用来存储所有小球；用来存储所有AI Ball
    + 我们需要可以很快速的遍历这个数据结构，同时删减特定元素的速度相对较快，所以选择ArrayList
  + PriorityQueue
    + 作用：记分板
    + 我们需要对所有Ball进行排序展现，同时进入数据需要随时排序
+ Demo to show functionality
  + 一个加速过的视频
