/*
 * math.h
 *
 *  Created on: 18/07/2015
 *      Author: cams7
 */

#ifndef INCLUDE_MATH_H_
#define INCLUDE_MATH_H_

extern int addition(int value1, int value2) asm("_addCalc");
extern int subtraction(int value1, int value2) asm("_subCalc");

#endif /* INCLUDE_MATH_H_ */
