#include <Arduino.h>

//Declared weak in Arduino.h to allow user redefinitions.
int atexit(void (*func)()) { return 0; }

// Weak empty variant initialization function.
// May be redefined by variant files.
void initVariant() __attribute__((weak));
void initVariant() { }

int main(void) {
	init();

	initVariant();

#if defined(USBCON)
	USBDevice.attach();
#endif
	
	setup();
    
	for (;;) {
		loop();
		if (serialEventRun) serialEventRun();
	}
        
	return 0;
}

int led = 13;

void setup(void) {
	pinMode(led, OUTPUT);	
}

void loop(void) {
	digitalWrite(led, HIGH);   
	delay(1000);               
	digitalWrite(led, LOW);    
	delay(1000);
}