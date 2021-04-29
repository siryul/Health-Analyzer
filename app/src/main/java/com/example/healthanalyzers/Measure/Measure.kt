package com.example.healthanalyzers.Measure

import kotlin.random.Random

public class Measure {

    fun getHeartRate(): Int {
        // 以一个95%概率获得一个正常值，5%概率获得一个不正常的值
        return if ((1..100).random() <= 95) {
            // 正常
            (60..100).random()
        } else {
            if ((0..1).random() == 0) {
                // 测量值偏小
                (40..59).random()
            } else {
                (101..150).random()
            }
        }
    }

    fun getWeight():Int {
        return (20..80).random()
    }

    fun getHigh() = (50..230).random()

    fun getBodyFat(): Float {
        // 成年人的体脂率正常范围分别是女性20%～25%，男性15%～18%
        // 运动员的体脂率可随运动项目而定。 一般男运动员为7%～15%，女运动员为12%—25%。
        return 0F
    }

    fun getArterialBloodOxygen(): Int {
        // 人体血氧含量的正常值为：动脉血150-230ml/L
        return if ((1..100).random() <= 95) {
            // 正常
            (150..230).random()
        } else {
            if ((0..1).random() == 0) {
                // 测量值偏小
                (100..149).random()
            } else {
                (231..280).random()
            }
        }
    }

    fun getVenousBloodOxygen(): Int {
        // 人体血氧含量的正常值为：静脉血100-180ml/L
        return if ((1..100).random() <= 95) {
            // 正常
            (100..180).random()
        } else {
            if ((0..1).random() == 0) {
                // 测量值偏小
                (65..99).random()
            } else {
                (181..230).random()
            }
        }
    }

    fun getPsySleepQuality(): Float {
        // 睡眠质量(暂时由 睡眠时间 代替，单位 h)
        // 前者随机数代表 h ，后者随机数代表 min
        return (3..13).random() + Random.nextFloat()
    }

    /**
     * 轻度高血压：收缩压140~159mmHg，舒张压90~99mHg;
     *
     * 中度高血压：收缩压160~179mmHg，舒张压100~109mmHg;
     *
     * 重度高血压：收缩压≧180mmHg，舒张压为≥110mmHg;
     *
     * 单纯收缩性高血压：收缩压为≧140mmHg，舒张压为<90mmHg;
     *
     * 低血压：收缩压为<90mmHg，舒张压为<60mmHg。
     */
    fun getSystolicPressure(): Int {
        // 正常人的血压值（收缩压）一般为：90~139mmHg
        return if ((1..100).random() <= 95) {
            // 正常
            (90..139).random()
        } else {
            if ((0..1).random() == 0) {
                // 测量值偏小
                (65..89).random()
            } else {
                (140..230).random()
            }
        }
    }

    fun getDiastolicPressure(): Int {
        // 正常人的血压值（舒张压）一般为：60~89mmHg
        return if ((1..100).random() <= 95) {
            // 正常
            (60..89).random()
        } else {
            if ((0..1).random() == 0) {
                // 测量值偏小
                (30..59).random()
            } else {
                (90..150).random()
            }
        }
    }

    fun getBloodSugar(): Int {
        // 一般空腹全血血糖为3.9～6.1毫摩尔/升(70～110毫克/分升)
        return if ((1..100).random() <= 95) {
            // 正常
            (70..110).random()
        } else {
            if ((0..1).random() == 0) {
                // 测量值偏小
                (55..69).random()
            } else {
                (111..220).random()
            }
        }
    }

    fun getTemperature(): Float {
        // 测量体温
        return (36..39).random() + Random.nextFloat()
    }
}