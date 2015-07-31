    .text
    .globl  _addCalc
	.globl  _subCalc
	.globl  _mulCalc
	.globl  _divCalc
_addCalc:
    movl    8(%esp), %eax
    addl    4(%esp), %eax
    ret
_subCalc:
    movl    8(%esp), %eax
    subl    4(%esp), %eax
    ret