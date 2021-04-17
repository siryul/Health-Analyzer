package com.example.healthanalyzers.Measure

class Measure {

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
        // 睡眠质量
        return 0f
    }
}