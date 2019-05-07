package ru.inforion.lab403.kopycat.cores.msp430.instructions.cpu.arithm

import ru.inforion.lab403.kopycat.cores.base.operands.AOperand
import ru.inforion.lab403.kopycat.cores.msp430.instructions.AMSP430Instruction
import ru.inforion.lab403.kopycat.modules.cores.MSP430Core

/**
 * Created by shiftdj on 13/02/18.
 */

class Mov(core: MSP430Core, size: Int, vararg operands: AOperand<MSP430Core>):
        AMSP430Instruction(core, Type.VOID, size, *operands) {
    override val mnem = "mov"

    override fun execute() = op2.value(core, op1.value(core))
}