quartz
======

quartz封装

解决问题：支持网站本身的的定时任务，比如：处理动态页面转静态，定时采集数据，定期更新访问量等类似linux cron任务

按照quartz特点进行封装：

1.按照项目结构，封装成层次清晰的接口供调用，提供model service领域的方式

2.通过管理后台直接可以查看每个任务的状况，新增，修改任务，可以立即对任务进行相关操作

没有任何技术难点，需要的是结构清晰，涉及到相关点debug分析

quatz问题

1.任务跟触发器都可以配置group name ，底层表结构上各自独立，完全可以配置一堆触发器，管理触发器
按个任务需要赋予具体任务，but它不让你这么干，触发器不能单独出来，他只是任务的附属物，只能拿到任务再去拿触发器


2.quartz任务运行错误，不能自行恢复，所以你需要管理状态，自己来恢复


