# hrd

华容道的求解算法
只有算法，无界面
go 和 java 的表示方式相同，思路近乎等同，求解速度略有不同。
后写的内容只是优化了速度。
棋局将棋子的坐标使用二进制表示 每种棋局 存储于一个int64(long) 变量中
所有可行解已全部计算完成，采用csv格式存储，可以保证全局最优解

[实现思路（旧）](https://github.com/formatjn2019/myblog/blob/main/docs/%E5%B0%8F%E7%A8%8B%E5%BA%8F/%E5%8D%8E%E5%AE%B9%E9%81%93%E7%9A%84%E7%AE%97%E6%B3%95%E5%AE%9E%E7%8E%B0.md)
[实现思路（新）](https://github.com/formatjn2019/myblog/blob/main/docs/%E5%B0%8F%E7%A8%8B%E5%BA%8F/%E5%8D%8E%E5%AE%B9%E9%81%93%E7%9A%84%E6%B1%82%E8%A7%A3%E7%AE%97%E6%B3%95%E9%87%8D%E5%86%99.md)
