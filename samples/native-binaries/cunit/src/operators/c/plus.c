#include "operators.h"

int plus(int a, int b) {
#ifdef PLUS_BROKEN
    return 2;
#else
	int result = a + b;
    return result;
#endif
}
