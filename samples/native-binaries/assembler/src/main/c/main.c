#include <stdio.h>
#include <sum.h>

void main(void) __attribute__ ((noreturn));

void main () {
	int value1 = 3;
	int value2 = 9;
	int result = sum(value1, value2);
	printf("%d + %d = %d\n", value1, value2, result);
}
