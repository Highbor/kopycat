package ru.inforion.lab403.kopycat.cores.x86.instructions.cpu.system

import ru.inforion.lab403.kopycat.cores.x86.hardware.systemdc.Prefixes
import ru.inforion.lab403.kopycat.cores.x86.instructions.AX86Instruction
import ru.inforion.lab403.kopycat.modules.cores.x86Core

/**
 * Created by a.gladkikh on 29.10.19.
 */
class Hlt(core: x86Core, opcode: ByteArray, prefs: Prefixes):
        AX86Instruction(core, Type.VOID, opcode, prefs) {
    override val mnem = "hlt"

    override fun execute() {
        core.cpu.halted = true
        if (core.cop.unhaltProcessor) {
            core.cpu.halted = false
            core.cop.unhaltProcessor = false
        } else {
            // processor halted here -> move eip back to hlt instruction
            core.cpu.regs.eip -= size
        }
    }
}