package ru.inforion.lab403.kopycat.cores.ppc.instructions.cpu.base.logicInt

import ru.inforion.lab403.kopycat.cores.base.operands.AOperand
import ru.inforion.lab403.kopycat.cores.ppc.instructions.APPCInstruction
import ru.inforion.lab403.kopycat.modules.cores.PPCCore



//XOR immediate shifted
class xoris(core: PPCCore, val condRegField: Long, val length: Boolean, val data: Long, vararg operands: AOperand<PPCCore>):
        APPCInstruction(core, Type.VOID, *operands) {
    override val mnem = "xoris"

    override fun execute() {
        val extImm = data shl 16
        op2.value(core, op1.value(core) xor extImm)
    }
}