* 这篇文章描述了股票分析中的各种算法 *

# 移动平均线

## 简单移动平均线(SMA)

把N天的收盘价加总然后再求平均值。

SMA(N) = 1/N * [CLOSE(D) + CLOSE(D - 1) + ... + CLOSE(D - N + 1)]


## 指数平滑移动平均线(EMA)

相比简单移动平均线, EMA是以指数递减加权的一定平均。指数值随着时间而成指数式递增,越近期的数据权重越大。EMA比SMA更加敏感。

计算公式:
EMAtoday=α * Pricetoday + ( 1 - α ) * EMAyesterday;
EMA1是没有定义的,它的值一般取为前面 N-1 个数值的平均值。

α为平滑系数,它的值为一般为 2/(N+1)。

一般来说,显示均线时可以用SMA, 而EMA一般用来计算MACD的值, N的周期取12,26. 那是因为以前我国实行一星期6天交易制,一个月平均交易26天。

在计算机递推计算时，可以写作：
EMAtoday= α * ( Pricetoday - EMAyesterday ) + EMAyesterday;

百度链接:

http://baike.baidu.com/link?url=ysPMFiP_wjXjXFDuV-EBQ8YVQDztP7F4E2SrWyzFVsY3m-epJLFkX9A_6RYrcvTO1JYSsknXx9utYuPihfsuda
http://zhidao.baidu.com/question/417909385.html
