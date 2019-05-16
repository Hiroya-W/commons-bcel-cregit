begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_package
package|package
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|generic
package|;
end_package

begin_comment
comment|/**  * Interface implementing the Visitor pattern programming style.  * I.e., a class that implements this interface can handle all types of  * instructions with the properly typed methods just by calling the accept()  * method.  *  */
end_comment

begin_interface
specifier|public
interface|interface
name|Visitor
block|{
name|void
name|visitStackInstruction
parameter_list|(
name|StackInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLocalVariableInstruction
parameter_list|(
name|LocalVariableInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitBranchInstruction
parameter_list|(
name|BranchInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLoadClass
parameter_list|(
name|LoadClass
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFieldInstruction
parameter_list|(
name|FieldInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIfInstruction
parameter_list|(
name|IfInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConversionInstruction
parameter_list|(
name|ConversionInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitPopInstruction
parameter_list|(
name|PopInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitStoreInstruction
parameter_list|(
name|StoreInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitTypedInstruction
parameter_list|(
name|TypedInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitSelect
parameter_list|(
name|Select
name|obj
parameter_list|)
function_decl|;
name|void
name|visitJsrInstruction
parameter_list|(
name|JsrInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitGotoInstruction
parameter_list|(
name|GotoInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitUnconditionalBranch
parameter_list|(
name|UnconditionalBranch
name|obj
parameter_list|)
function_decl|;
name|void
name|visitPushInstruction
parameter_list|(
name|PushInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitArithmeticInstruction
parameter_list|(
name|ArithmeticInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitCPInstruction
parameter_list|(
name|CPInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitInvokeInstruction
parameter_list|(
name|InvokeInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitArrayInstruction
parameter_list|(
name|ArrayInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitAllocationInstruction
parameter_list|(
name|AllocationInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitReturnInstruction
parameter_list|(
name|ReturnInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFieldOrMethod
parameter_list|(
name|FieldOrMethod
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConstantPushInstruction
parameter_list|(
name|ConstantPushInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitExceptionThrower
parameter_list|(
name|ExceptionThrower
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLoadInstruction
parameter_list|(
name|LoadInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitVariableLengthInstruction
parameter_list|(
name|VariableLengthInstruction
name|obj
parameter_list|)
function_decl|;
name|void
name|visitStackProducer
parameter_list|(
name|StackProducer
name|obj
parameter_list|)
function_decl|;
name|void
name|visitStackConsumer
parameter_list|(
name|StackConsumer
name|obj
parameter_list|)
function_decl|;
name|void
name|visitACONST_NULL
parameter_list|(
name|ACONST_NULL
name|obj
parameter_list|)
function_decl|;
name|void
name|visitGETSTATIC
parameter_list|(
name|GETSTATIC
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIF_ICMPLT
parameter_list|(
name|IF_ICMPLT
name|obj
parameter_list|)
function_decl|;
name|void
name|visitMONITOREXIT
parameter_list|(
name|MONITOREXIT
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIFLT
parameter_list|(
name|IFLT
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLSTORE
parameter_list|(
name|LSTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitPOP2
parameter_list|(
name|POP2
name|obj
parameter_list|)
function_decl|;
name|void
name|visitBASTORE
parameter_list|(
name|BASTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitISTORE
parameter_list|(
name|ISTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitCHECKCAST
parameter_list|(
name|CHECKCAST
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFCMPG
parameter_list|(
name|FCMPG
name|obj
parameter_list|)
function_decl|;
name|void
name|visitI2F
parameter_list|(
name|I2F
name|obj
parameter_list|)
function_decl|;
name|void
name|visitATHROW
parameter_list|(
name|ATHROW
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDCMPL
parameter_list|(
name|DCMPL
name|obj
parameter_list|)
function_decl|;
name|void
name|visitARRAYLENGTH
parameter_list|(
name|ARRAYLENGTH
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDUP
parameter_list|(
name|DUP
name|obj
parameter_list|)
function_decl|;
name|void
name|visitINVOKESTATIC
parameter_list|(
name|INVOKESTATIC
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLCONST
parameter_list|(
name|LCONST
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDREM
parameter_list|(
name|DREM
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIFGE
parameter_list|(
name|IFGE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitCALOAD
parameter_list|(
name|CALOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLASTORE
parameter_list|(
name|LASTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitI2D
parameter_list|(
name|I2D
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDADD
parameter_list|(
name|DADD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitINVOKESPECIAL
parameter_list|(
name|INVOKESPECIAL
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIAND
parameter_list|(
name|IAND
name|obj
parameter_list|)
function_decl|;
name|void
name|visitPUTFIELD
parameter_list|(
name|PUTFIELD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitILOAD
parameter_list|(
name|ILOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDLOAD
parameter_list|(
name|DLOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDCONST
parameter_list|(
name|DCONST
name|obj
parameter_list|)
function_decl|;
name|void
name|visitNEW
parameter_list|(
name|NEW
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIFNULL
parameter_list|(
name|IFNULL
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLSUB
parameter_list|(
name|LSUB
name|obj
parameter_list|)
function_decl|;
name|void
name|visitL2I
parameter_list|(
name|L2I
name|obj
parameter_list|)
function_decl|;
name|void
name|visitISHR
parameter_list|(
name|ISHR
name|obj
parameter_list|)
function_decl|;
name|void
name|visitTABLESWITCH
parameter_list|(
name|TABLESWITCH
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIINC
parameter_list|(
name|IINC
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDRETURN
parameter_list|(
name|DRETURN
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFSTORE
parameter_list|(
name|FSTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDASTORE
parameter_list|(
name|DASTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIALOAD
parameter_list|(
name|IALOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDDIV
parameter_list|(
name|DDIV
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIF_ICMPGE
parameter_list|(
name|IF_ICMPGE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLAND
parameter_list|(
name|LAND
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIDIV
parameter_list|(
name|IDIV
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLOR
parameter_list|(
name|LOR
name|obj
parameter_list|)
function_decl|;
name|void
name|visitCASTORE
parameter_list|(
name|CASTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFREM
parameter_list|(
name|FREM
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLDC
parameter_list|(
name|LDC
name|obj
parameter_list|)
function_decl|;
name|void
name|visitBIPUSH
parameter_list|(
name|BIPUSH
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDSTORE
parameter_list|(
name|DSTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitF2L
parameter_list|(
name|F2L
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFMUL
parameter_list|(
name|FMUL
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLLOAD
parameter_list|(
name|LLOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitJSR
parameter_list|(
name|JSR
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFSUB
parameter_list|(
name|FSUB
name|obj
parameter_list|)
function_decl|;
name|void
name|visitSASTORE
parameter_list|(
name|SASTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitALOAD
parameter_list|(
name|ALOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDUP2_X2
parameter_list|(
name|DUP2_X2
name|obj
parameter_list|)
function_decl|;
name|void
name|visitRETURN
parameter_list|(
name|RETURN
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDALOAD
parameter_list|(
name|DALOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitSIPUSH
parameter_list|(
name|SIPUSH
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDSUB
parameter_list|(
name|DSUB
name|obj
parameter_list|)
function_decl|;
name|void
name|visitL2F
parameter_list|(
name|L2F
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIF_ICMPGT
parameter_list|(
name|IF_ICMPGT
name|obj
parameter_list|)
function_decl|;
name|void
name|visitF2D
parameter_list|(
name|F2D
name|obj
parameter_list|)
function_decl|;
name|void
name|visitI2L
parameter_list|(
name|I2L
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIF_ACMPNE
parameter_list|(
name|IF_ACMPNE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitPOP
parameter_list|(
name|POP
name|obj
parameter_list|)
function_decl|;
name|void
name|visitI2S
parameter_list|(
name|I2S
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIFEQ
parameter_list|(
name|IFEQ
name|obj
parameter_list|)
function_decl|;
name|void
name|visitSWAP
parameter_list|(
name|SWAP
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIOR
parameter_list|(
name|IOR
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIREM
parameter_list|(
name|IREM
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIASTORE
parameter_list|(
name|IASTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitNEWARRAY
parameter_list|(
name|NEWARRAY
name|obj
parameter_list|)
function_decl|;
name|void
name|visitINVOKEINTERFACE
parameter_list|(
name|INVOKEINTERFACE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitINEG
parameter_list|(
name|INEG
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLCMP
parameter_list|(
name|LCMP
name|obj
parameter_list|)
function_decl|;
name|void
name|visitJSR_W
parameter_list|(
name|JSR_W
name|obj
parameter_list|)
function_decl|;
name|void
name|visitMULTIANEWARRAY
parameter_list|(
name|MULTIANEWARRAY
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDUP_X2
parameter_list|(
name|DUP_X2
name|obj
parameter_list|)
function_decl|;
name|void
name|visitSALOAD
parameter_list|(
name|SALOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIFNONNULL
parameter_list|(
name|IFNONNULL
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDMUL
parameter_list|(
name|DMUL
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIFNE
parameter_list|(
name|IFNE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIF_ICMPLE
parameter_list|(
name|IF_ICMPLE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLDC2_W
parameter_list|(
name|LDC2_W
name|obj
parameter_list|)
function_decl|;
name|void
name|visitGETFIELD
parameter_list|(
name|GETFIELD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLADD
parameter_list|(
name|LADD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitNOP
parameter_list|(
name|NOP
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFALOAD
parameter_list|(
name|FALOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitINSTANCEOF
parameter_list|(
name|INSTANCEOF
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIFLE
parameter_list|(
name|IFLE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLXOR
parameter_list|(
name|LXOR
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLRETURN
parameter_list|(
name|LRETURN
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFCONST
parameter_list|(
name|FCONST
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIUSHR
parameter_list|(
name|IUSHR
name|obj
parameter_list|)
function_decl|;
name|void
name|visitBALOAD
parameter_list|(
name|BALOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDUP2
parameter_list|(
name|DUP2
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIF_ACMPEQ
parameter_list|(
name|IF_ACMPEQ
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIMPDEP1
parameter_list|(
name|IMPDEP1
name|obj
parameter_list|)
function_decl|;
name|void
name|visitMONITORENTER
parameter_list|(
name|MONITORENTER
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLSHL
parameter_list|(
name|LSHL
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDCMPG
parameter_list|(
name|DCMPG
name|obj
parameter_list|)
function_decl|;
name|void
name|visitD2L
parameter_list|(
name|D2L
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIMPDEP2
parameter_list|(
name|IMPDEP2
name|obj
parameter_list|)
function_decl|;
name|void
name|visitL2D
parameter_list|(
name|L2D
name|obj
parameter_list|)
function_decl|;
name|void
name|visitRET
parameter_list|(
name|RET
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIFGT
parameter_list|(
name|IFGT
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIXOR
parameter_list|(
name|IXOR
name|obj
parameter_list|)
function_decl|;
name|void
name|visitINVOKEVIRTUAL
parameter_list|(
name|INVOKEVIRTUAL
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.0      */
name|void
name|visitINVOKEDYNAMIC
parameter_list|(
name|INVOKEDYNAMIC
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFASTORE
parameter_list|(
name|FASTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIRETURN
parameter_list|(
name|IRETURN
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIF_ICMPNE
parameter_list|(
name|IF_ICMPNE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFLOAD
parameter_list|(
name|FLOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLDIV
parameter_list|(
name|LDIV
name|obj
parameter_list|)
function_decl|;
name|void
name|visitPUTSTATIC
parameter_list|(
name|PUTSTATIC
name|obj
parameter_list|)
function_decl|;
name|void
name|visitAALOAD
parameter_list|(
name|AALOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitD2I
parameter_list|(
name|D2I
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIF_ICMPEQ
parameter_list|(
name|IF_ICMPEQ
name|obj
parameter_list|)
function_decl|;
name|void
name|visitAASTORE
parameter_list|(
name|AASTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitARETURN
parameter_list|(
name|ARETURN
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDUP2_X1
parameter_list|(
name|DUP2_X1
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFNEG
parameter_list|(
name|FNEG
name|obj
parameter_list|)
function_decl|;
name|void
name|visitGOTO_W
parameter_list|(
name|GOTO_W
name|obj
parameter_list|)
function_decl|;
name|void
name|visitD2F
parameter_list|(
name|D2F
name|obj
parameter_list|)
function_decl|;
name|void
name|visitGOTO
parameter_list|(
name|GOTO
name|obj
parameter_list|)
function_decl|;
name|void
name|visitISUB
parameter_list|(
name|ISUB
name|obj
parameter_list|)
function_decl|;
name|void
name|visitF2I
parameter_list|(
name|F2I
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDNEG
parameter_list|(
name|DNEG
name|obj
parameter_list|)
function_decl|;
name|void
name|visitICONST
parameter_list|(
name|ICONST
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFDIV
parameter_list|(
name|FDIV
name|obj
parameter_list|)
function_decl|;
name|void
name|visitI2B
parameter_list|(
name|I2B
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLNEG
parameter_list|(
name|LNEG
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLREM
parameter_list|(
name|LREM
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIMUL
parameter_list|(
name|IMUL
name|obj
parameter_list|)
function_decl|;
name|void
name|visitIADD
parameter_list|(
name|IADD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLSHR
parameter_list|(
name|LSHR
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLOOKUPSWITCH
parameter_list|(
name|LOOKUPSWITCH
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDUP_X1
parameter_list|(
name|DUP_X1
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFCMPL
parameter_list|(
name|FCMPL
name|obj
parameter_list|)
function_decl|;
name|void
name|visitI2C
parameter_list|(
name|I2C
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLMUL
parameter_list|(
name|LMUL
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLUSHR
parameter_list|(
name|LUSHR
name|obj
parameter_list|)
function_decl|;
name|void
name|visitISHL
parameter_list|(
name|ISHL
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLALOAD
parameter_list|(
name|LALOAD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitASTORE
parameter_list|(
name|ASTORE
name|obj
parameter_list|)
function_decl|;
name|void
name|visitANEWARRAY
parameter_list|(
name|ANEWARRAY
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFRETURN
parameter_list|(
name|FRETURN
name|obj
parameter_list|)
function_decl|;
name|void
name|visitFADD
parameter_list|(
name|FADD
name|obj
parameter_list|)
function_decl|;
name|void
name|visitBREAKPOINT
parameter_list|(
name|BREAKPOINT
name|obj
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

