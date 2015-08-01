/*
 * main.cpp
 *
 *  Created on: 24/07/2015
 *      Author: cams7
 */

//#include <inttypes.h>
//#include <avr/io.h>
//#include <avr/interrupt.h>
//#include <util/delay.h>

#include <stdio.h>

extern "C" {
	#include <blink.h>
	#include <print.h>
}

//#define LED_BIT PB5
//#define LED_HIGH PORTB |= _BV(LED_BIT)
//#define LED_LOW PORTB &= ~(_BV(LED_BIT))

//void main(void) __attribute__ ((noreturn));

//void main () {
int main () {
	int delayInMs = getDelayInMs();
	printf("Delay value is %d\n\n", delayInMs);
	
	printf("Calc values:\n");
	printCalc(delayInMs);
  
	/*uint8_t led_state = 1;
	DDRB |= (1 << LED_BIT);

	while (1) {
		if (led_state)
			LED_HIGH;
		else
			LED_LOW;

		led_state = !led_state;
		_delay_ms(getDelayInMs());
	}*/
	return 0;
}
