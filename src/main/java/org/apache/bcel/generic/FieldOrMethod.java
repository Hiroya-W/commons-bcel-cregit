begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  */
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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|Const
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ConstantCP
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ConstantNameAndType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ConstantPool
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|ConstantUtf8
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|Utility
import|;
end_import

begin_comment
comment|/**  * Super class for InvokeInstruction and FieldInstruction, since they have some methods in common!  */
end_comment

begin_class
specifier|public
specifier|abstract
class|class
name|FieldOrMethod
extends|extends
name|CPInstruction
implements|implements
name|LoadClass
block|{
comment|/**      * Empty constructor needed for Instruction.readInstruction. Not to be used otherwise.      */
name|FieldOrMethod
parameter_list|()
block|{
comment|// no init
block|}
comment|/**      * @param index to constant pool      */
specifier|protected
name|FieldOrMethod
parameter_list|(
specifier|final
name|short
name|opcode
parameter_list|,
specifier|final
name|int
name|index
parameter_list|)
block|{
name|super
argument_list|(
name|opcode
argument_list|,
name|index
argument_list|)
expr_stmt|;
block|}
comment|/**      * @return name of the referenced class/interface      * @deprecated If the instruction references an array class, this method will return "java.lang.Object". For code      *             generated by Java 1.5, this answer is sometimes wrong (e.g., if the "clone()" method is called on an      *             array). A better idea is to use the {@link #getReferenceType(ConstantPoolGen)} method, which correctly      *             distinguishes between class types and array types.      *      */
annotation|@
name|Deprecated
specifier|public
name|String
name|getClassName
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
specifier|final
name|ConstantPool
name|cp
init|=
name|cpg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
specifier|final
name|ConstantCP
name|cmr
init|=
operator|(
name|ConstantCP
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|super
operator|.
name|getIndex
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|String
name|className
init|=
name|cp
operator|.
name|getConstantString
argument_list|(
name|cmr
operator|.
name|getClassIndex
argument_list|()
argument_list|,
name|Const
operator|.
name|CONSTANT_Class
argument_list|)
decl_stmt|;
if|if
condition|(
name|className
operator|.
name|startsWith
argument_list|(
literal|"["
argument_list|)
condition|)
block|{
comment|// Turn array classes into java.lang.Object.
return|return
literal|"java.lang.Object"
return|;
block|}
return|return
name|Utility
operator|.
name|pathToPackage
argument_list|(
name|className
argument_list|)
return|;
block|}
comment|/**      * @return type of the referenced class/interface      * @deprecated If the instruction references an array class, the ObjectType returned will be invalid. Use      *             getReferenceType() instead.      */
annotation|@
name|Deprecated
specifier|public
name|ObjectType
name|getClassType
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
return|return
name|ObjectType
operator|.
name|getInstance
argument_list|(
name|getClassName
argument_list|(
name|cpg
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Gets the ObjectType of the method return or field.      *      * @return type of the referenced class/interface      * @throws ClassGenException when the field is (or method returns) an array,      */
annotation|@
name|Override
specifier|public
name|ObjectType
name|getLoadClassType
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
specifier|final
name|ReferenceType
name|rt
init|=
name|getReferenceType
argument_list|(
name|cpg
argument_list|)
decl_stmt|;
if|if
condition|(
name|rt
operator|instanceof
name|ObjectType
condition|)
block|{
return|return
operator|(
name|ObjectType
operator|)
name|rt
return|;
block|}
throw|throw
operator|new
name|ClassGenException
argument_list|(
name|rt
operator|.
name|getClass
argument_list|()
operator|.
name|getCanonicalName
argument_list|()
operator|+
literal|" "
operator|+
name|rt
operator|.
name|getSignature
argument_list|()
operator|+
literal|" does not represent an ObjectType"
argument_list|)
throw|;
block|}
comment|/**      * @return name of referenced method/field.      */
specifier|public
name|String
name|getName
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
specifier|final
name|ConstantPool
name|cp
init|=
name|cpg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
specifier|final
name|ConstantCP
name|cmr
init|=
operator|(
name|ConstantCP
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|super
operator|.
name|getIndex
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|ConstantNameAndType
name|cnat
init|=
operator|(
name|ConstantNameAndType
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|cmr
operator|.
name|getNameAndTypeIndex
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|(
operator|(
name|ConstantUtf8
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|cnat
operator|.
name|getNameIndex
argument_list|()
argument_list|)
operator|)
operator|.
name|getBytes
argument_list|()
return|;
block|}
comment|/**      * Gets the reference type representing the class, interface, or array class referenced by the instruction.      *      * @param cpg the ConstantPoolGen used to create the instruction      * @return an ObjectType (if the referenced class type is a class or interface), or an ArrayType (if the referenced      *         class type is an array class)      */
specifier|public
name|ReferenceType
name|getReferenceType
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
specifier|final
name|ConstantPool
name|cp
init|=
name|cpg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
specifier|final
name|ConstantCP
name|cmr
init|=
operator|(
name|ConstantCP
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|super
operator|.
name|getIndex
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|className
init|=
name|cp
operator|.
name|getConstantString
argument_list|(
name|cmr
operator|.
name|getClassIndex
argument_list|()
argument_list|,
name|Const
operator|.
name|CONSTANT_Class
argument_list|)
decl_stmt|;
if|if
condition|(
name|className
operator|.
name|startsWith
argument_list|(
literal|"["
argument_list|)
condition|)
block|{
return|return
operator|(
name|ArrayType
operator|)
name|Type
operator|.
name|getType
argument_list|(
name|className
argument_list|)
return|;
block|}
name|className
operator|=
name|Utility
operator|.
name|pathToPackage
argument_list|(
name|className
argument_list|)
expr_stmt|;
return|return
name|ObjectType
operator|.
name|getInstance
argument_list|(
name|className
argument_list|)
return|;
block|}
comment|/**      * @return signature of referenced method/field.      */
specifier|public
name|String
name|getSignature
parameter_list|(
specifier|final
name|ConstantPoolGen
name|cpg
parameter_list|)
block|{
specifier|final
name|ConstantPool
name|cp
init|=
name|cpg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
specifier|final
name|ConstantCP
name|cmr
init|=
operator|(
name|ConstantCP
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|super
operator|.
name|getIndex
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|ConstantNameAndType
name|cnat
init|=
operator|(
name|ConstantNameAndType
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|cmr
operator|.
name|getNameAndTypeIndex
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|(
operator|(
name|ConstantUtf8
operator|)
name|cp
operator|.
name|getConstant
argument_list|(
name|cnat
operator|.
name|getSignatureIndex
argument_list|()
argument_list|)
operator|)
operator|.
name|getBytes
argument_list|()
return|;
block|}
block|}
end_class

end_unit

