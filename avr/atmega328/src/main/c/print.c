/*
 * print.c
 *
 *  Created on: 18/07/2015
 *      Author: cams7
 */

#include <print.h>

#include <stdio.h>

#include <blink.h>
#include <math.h>

void print(void) {
	int delayInMs = getDelayInMs();
	printf("%d + %d = %d\n", delayInMs, delayInMs, addition(delayInMs, delayInMs));
	printf("%d - %d = %d\n", delayInMs, delayInMs, subtraction(delayInMs, delayInMs));
}