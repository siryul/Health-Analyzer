package com.example.healthanalyzers.bean

// imageId 代表相应设备的图片 id
// type 有效值与对应关系：
//  1 ————> 心率仪
//  2 ————> 手环/表
//  3 ————> 身高测定仪
//  4 ————> 血氧测定仪
//  5 ————> 血压计
//  6 ————> 血糖仪
//  7 ————> 称
//  8 ————> 体温计
class Devices(val id: Int, val name: String, val imageId: Int, val type: Int)