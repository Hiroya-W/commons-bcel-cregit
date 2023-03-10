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
name|classfile
package|;
end_package

begin_comment
comment|/**  * Interface to make use of the Visitor pattern programming style. I.e. a class that implements this interface can  * traverse the contents of a Java class just by calling the 'accept' method which all classes have.  */
end_comment

begin_interface
specifier|public
interface|interface
name|Visitor
block|{
comment|/**      * @since 6.0      */
name|void
name|visitAnnotation
parameter_list|(
name|Annotations
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.0      */
name|void
name|visitAnnotationDefault
parameter_list|(
name|AnnotationDefault
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.0      */
name|void
name|visitAnnotationEntry
parameter_list|(
name|AnnotationEntry
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.0      */
name|void
name|visitBootstrapMethods
parameter_list|(
name|BootstrapMethods
name|obj
parameter_list|)
function_decl|;
name|void
name|visitCode
parameter_list|(
name|Code
name|obj
parameter_list|)
function_decl|;
name|void
name|visitCodeException
parameter_list|(
name|CodeException
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConstantClass
parameter_list|(
name|ConstantClass
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConstantDouble
parameter_list|(
name|ConstantDouble
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.3      */
specifier|default
name|void
name|visitConstantDynamic
parameter_list|(
specifier|final
name|ConstantDynamic
name|constantDynamic
parameter_list|)
block|{
comment|// empty
block|}
name|void
name|visitConstantFieldref
parameter_list|(
name|ConstantFieldref
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConstantFloat
parameter_list|(
name|ConstantFloat
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConstantInteger
parameter_list|(
name|ConstantInteger
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConstantInterfaceMethodref
parameter_list|(
name|ConstantInterfaceMethodref
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConstantInvokeDynamic
parameter_list|(
name|ConstantInvokeDynamic
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConstantLong
parameter_list|(
name|ConstantLong
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.0      */
name|void
name|visitConstantMethodHandle
parameter_list|(
name|ConstantMethodHandle
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConstantMethodref
parameter_list|(
name|ConstantMethodref
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.0      */
name|void
name|visitConstantMethodType
parameter_list|(
name|ConstantMethodType
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.1      */
name|void
name|visitConstantModule
parameter_list|(
name|ConstantModule
name|constantModule
parameter_list|)
function_decl|;
name|void
name|visitConstantNameAndType
parameter_list|(
name|ConstantNameAndType
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.1      */
name|void
name|visitConstantPackage
parameter_list|(
name|ConstantPackage
name|constantPackage
parameter_list|)
function_decl|;
name|void
name|visitConstantPool
parameter_list|(
name|ConstantPool
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConstantString
parameter_list|(
name|ConstantString
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConstantUtf8
parameter_list|(
name|ConstantUtf8
name|obj
parameter_list|)
function_decl|;
name|void
name|visitConstantValue
parameter_list|(
name|ConstantValue
name|obj
parameter_list|)
function_decl|;
name|void
name|visitDeprecated
parameter_list|(
name|Deprecated
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.0      */
name|void
name|visitEnclosingMethod
parameter_list|(
name|EnclosingMethod
name|obj
parameter_list|)
function_decl|;
name|void
name|visitExceptionTable
parameter_list|(
name|ExceptionTable
name|obj
parameter_list|)
function_decl|;
name|void
name|visitField
parameter_list|(
name|Field
name|obj
parameter_list|)
function_decl|;
name|void
name|visitInnerClass
parameter_list|(
name|InnerClass
name|obj
parameter_list|)
function_decl|;
name|void
name|visitInnerClasses
parameter_list|(
name|InnerClasses
name|obj
parameter_list|)
function_decl|;
name|void
name|visitJavaClass
parameter_list|(
name|JavaClass
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLineNumber
parameter_list|(
name|LineNumber
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLineNumberTable
parameter_list|(
name|LineNumberTable
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLocalVariable
parameter_list|(
name|LocalVariable
name|obj
parameter_list|)
function_decl|;
name|void
name|visitLocalVariableTable
parameter_list|(
name|LocalVariableTable
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.0      */
name|void
name|visitLocalVariableTypeTable
parameter_list|(
name|LocalVariableTypeTable
name|obj
parameter_list|)
function_decl|;
name|void
name|visitMethod
parameter_list|(
name|Method
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.4.0      */
specifier|default
name|void
name|visitMethodParameter
parameter_list|(
specifier|final
name|MethodParameter
name|obj
parameter_list|)
block|{
comment|// empty
block|}
comment|/**      * @since 6.0      */
name|void
name|visitMethodParameters
parameter_list|(
name|MethodParameters
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.4.0      */
specifier|default
name|void
name|visitModule
parameter_list|(
specifier|final
name|Module
name|constantModule
parameter_list|)
block|{
comment|// empty
block|}
comment|/**      * @since 6.4.0      */
specifier|default
name|void
name|visitModuleExports
parameter_list|(
specifier|final
name|ModuleExports
name|constantModule
parameter_list|)
block|{
comment|// empty
block|}
comment|/**      * @since 6.4.0      */
specifier|default
name|void
name|visitModuleMainClass
parameter_list|(
specifier|final
name|ModuleMainClass
name|obj
parameter_list|)
block|{
comment|// empty
block|}
comment|/**      * @since 6.4.0      */
specifier|default
name|void
name|visitModuleOpens
parameter_list|(
specifier|final
name|ModuleOpens
name|constantModule
parameter_list|)
block|{
comment|// empty
block|}
comment|/**      * @since 6.4.0      */
specifier|default
name|void
name|visitModulePackages
parameter_list|(
specifier|final
name|ModulePackages
name|constantModule
parameter_list|)
block|{
comment|// empty
block|}
comment|/**      * @since 6.4.0      */
specifier|default
name|void
name|visitModuleProvides
parameter_list|(
specifier|final
name|ModuleProvides
name|constantModule
parameter_list|)
block|{
comment|// empty
block|}
comment|/**      * @since 6.4.0      */
specifier|default
name|void
name|visitModuleRequires
parameter_list|(
specifier|final
name|ModuleRequires
name|constantModule
parameter_list|)
block|{
comment|// empty
block|}
comment|/**      * @since 6.4.0      */
specifier|default
name|void
name|visitNestHost
parameter_list|(
specifier|final
name|NestHost
name|obj
parameter_list|)
block|{
comment|// empty
block|}
comment|/**      * @since 6.4.0      */
specifier|default
name|void
name|visitNestMembers
parameter_list|(
specifier|final
name|NestMembers
name|obj
parameter_list|)
block|{
comment|// empty
block|}
comment|/**      * @since 6.0      */
name|void
name|visitParameterAnnotation
parameter_list|(
name|ParameterAnnotations
name|obj
parameter_list|)
function_decl|;
comment|/**      * @since 6.0      */
name|void
name|visitParameterAnnotationEntry
parameter_list|(
name|ParameterAnnotationEntry
name|obj
parameter_list|)
function_decl|;
name|void
name|visitSignature
parameter_list|(
name|Signature
name|obj
parameter_list|)
function_decl|;
name|void
name|visitSourceFile
parameter_list|(
name|SourceFile
name|obj
parameter_list|)
function_decl|;
name|void
name|visitStackMap
parameter_list|(
name|StackMap
name|obj
parameter_list|)
function_decl|;
name|void
name|visitStackMapEntry
parameter_list|(
name|StackMapEntry
name|obj
parameter_list|)
function_decl|;
name|void
name|visitSynthetic
parameter_list|(
name|Synthetic
name|obj
parameter_list|)
function_decl|;
name|void
name|visitUnknown
parameter_list|(
name|Unknown
name|obj
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

