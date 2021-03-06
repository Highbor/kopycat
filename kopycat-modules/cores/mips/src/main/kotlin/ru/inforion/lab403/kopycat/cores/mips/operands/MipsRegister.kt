/*
 *
 * This file is part of Kopycat emulator software.
 *
 * Copyright (C) 2020 INFORION, LLC
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * Non-free licenses may also be purchased from INFORION, LLC, 
 * for users who do not want their programs protected by the GPL. 
 * Contact us for details kopycat@inforion.ru
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */
package ru.inforion.lab403.kopycat.cores.mips.operands

import ru.inforion.lab403.kopycat.cores.base.enums.Datatype.DWORD
import ru.inforion.lab403.kopycat.cores.base.operands.AOperand.Access.ANY
import ru.inforion.lab403.kopycat.cores.base.operands.ARegister
import ru.inforion.lab403.kopycat.cores.mips.enums.Designation
import ru.inforion.lab403.kopycat.cores.mips.hardware.processors.ProcType
import ru.inforion.lab403.kopycat.modules.cores.MipsCore



abstract class MipsRegister<E: Enum<E>>(
        val core: ProcType,
        val designation: Designation,
        val desc: Enum<E>
) : ARegister<MipsCore>(desc.ordinal, ANY, DWORD) {

    companion object {
        fun any(core: ProcType, designation: Designation, reg: Int, sel: Int): MipsRegister<*> = when (core) {
            ProcType.CentralProc -> GPR(reg)
            ProcType.SystemControlCop -> when (designation) {
                Designation.General -> CPR(reg, sel)
                Designation.Control -> TODO("Bank is reserved")
            }
            ProcType.FloatingPointCop -> when (designation) {
                Designation.General -> FPR(reg)
                Designation.Control -> FCR(reg)
            }
            ProcType.ImplementSpecCop -> HWR(reg)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MipsRegister<*>) return false
        if (!super.equals(other)) return false

        if (desc != other.desc) return false
        if (core != other.core) return false
        if (designation != other.designation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + desc.ordinal
        result = 31 * result + core.hashCode()
        result = 31 * result + designation.hashCode()
        return result
    }

//    // NOTE: be careful Java stack smash may occur though fault of this field! :(
//    protected val desc get() = when (core) {
//        ProcType.CentralProc -> first<eGPR> { it.id == reg }
//        ProcType.SystemControlCop -> when (designation) {
//            Designation.General -> first<eCPR> { it.id == reg && it.sel == sel }
//            Designation.Control -> TODO("Bank is reserved")
//        }
//        ProcType.FloatingPointCop -> when (designation) {
//            Designation.General -> first<eFPR> { it.id == reg }
//            Designation.Control -> first<eFCR> { it.id == reg }
//        }
//        ProcType.ImplementSpecCop -> TODO("Implementation specific not supported")
//    }

    override fun toString(): String = "$${desc.name.toLowerCase()}"
}