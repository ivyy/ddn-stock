package com.ddn.stock.lib;

import org.springframework.stereotype.Component;

@Component
public class CandleStickLib {

  private double PERCENT = 0.01;

  /*
  -- 大阳线
  - 开盘价小于收盘价
  - 实体部分相对于开盘价上涨超过4%
  - 上影线很短，小于当日价格波动的20%
  - 下影线很短，小于当日价格波动的20%
  - 实体部分占75%以上
 */
  public boolean longRed(Candle candle) {
    return (candle.close > candle.open)
        && candle.body() / candle.open >= 4 * PERCENT
        && candle.upperShadow()  < candle.range() * 0.2
        && candle.lowerShadow() < candle.range() * 0.2
        && candle.body() / candle.range() > 75 * PERCENT
        ;
  }

  /*
    - 短阳线
    实体部分相对小的阳线,小于 4%
   */
  public boolean shortRed(Candle candle) {
    return (candle.close > candle.open)
        && candle.body() / candle.open < 4 * PERCENT
        && candle.upperShadow()  < candle.range() * 0.2
        && candle.lowerShadow() < candle.range() * 0.2
        && candle.body() / candle.range() > 75 * PERCENT
        ;
  }

  public boolean red(Candle candle) {
    return candle.close > candle.open;
  }

  public boolean green(Candle candle) {
    return candle.close < candle.open;
  }

  /*
    -- 大阴线
    - 开盘价大于收盘价
    - 实体部分相对于开盘下跌超过4%
    - 上影线很短，小于当日价格波动的20%
    - 下影线很短，小于当日价格波动的20%
    - 实体部分占75%以上
   */
  public boolean longGreen(Candle candle) {

    return (candle.close < candle.open)
        && candle.body() / candle.open >= 4 * PERCENT
        && candle.upperShadow() / candle.range() < 0.2
        && candle.lowerShadow() / candle.range() < 0.2
        && candle.body() / candle.range() > 75 * PERCENT
        ;
  }

  public boolean shortGreen(Candle candle) {
    return (candle.close < candle.open)
        && candle.body() / candle.open < 4 * PERCENT
        && candle.upperShadow() / candle.range() < 0.2
        && candle.lowerShadow() / candle.range() < 0.2
        && candle.body() / candle.range() > 75 * PERCENT
        ;
  }

  //十字星
  public boolean doji(Candle candle) {
    //上影线或者下影线比实体部分长,
    //实体的比例小于当前中间价=(开盘+收盘)/2的1%
    return (candle.upperShadow() > candle.body() || candle.lowerShadow() > candle.body())
        && candle.body() / ((candle.open + candle.close) * 0.5) < 0.01;
  }

  //长脚十字星
  public boolean longLeggedDoji(Candle candle) {
    return doji(candle) && candle.lowerShadow() > candle.upperShadow() * 2;
  }

  //墓碑十字星
  public boolean tombDoji(Candle candle) {
    return doji(candle) && candle.upperShadow() > candle.lowerShadow() * 2;
  }

  /*
    -- 锤子线
    - 在高位时，也叫“上吊线”，表示看空，但是在单根K先的形态判断上，不考虑位置。
   */
  public boolean hammer(Candle candle) {
    return candle.body()  > 0
        && candle.lowerShadow() > 2 * candle.upperShadow()
        && candle.lowerShadow() > candle.body() * 1.2
        && candle.body() > candle.range() * 0.1
        && candle.body() < candle.range() * 0.4
        ;
  }

  /*
    -- 纺锤线
    - 代表买卖双方势均力敌
   */
  public boolean koma(Candle candle) {
    return candle.body() > candle.range() * 0.2 //实体的长度不能太小，否则就是十字星了
        && candle.body() < candle.range() * 0.6
        && Math.max(candle.upperShadow(), candle.lowerShadow())  > Math.min(candle.upperShadow(), candle.lowerShadow()) * 0.8 //上下影线的比例不能太大
        && Math.max(candle.upperShadow(), candle.lowerShadow())  < Math.min(candle.upperShadow(), candle.lowerShadow()) * 1.6
        && Math.min(candle.upperShadow(), candle.lowerShadow()) > candle.body() * 0.5 //影线不能太短
        ;
  }

  /*
    -- 星蜡烛图 - 之一
    星蜡烛图在反转形态中扮演重要的角色。
    第一根为长阳线实体
    第二根为绿色的短实体,并且存在跳空
   */
  public boolean redStar(Candle first, Candle second) {
    return longRed(first) && doji(second) && green(second) && second.open < first.close;
  }

  public boolean greenStar(Candle first, Candle second) {
    return longGreen(first) && doji(second) && red(second) && second.open > first.close;
  }

}
