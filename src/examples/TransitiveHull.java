begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|Constants
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
name|Repository
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
name|ClassParser
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
name|ConstantClass
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
name|ConstantFieldref
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
name|ConstantInterfaceMethodref
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
name|ConstantMethodref
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
name|JavaClass
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
name|generic
operator|.
name|ArrayType
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
name|generic
operator|.
name|ObjectType
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
name|generic
operator|.
name|Type
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
name|util
operator|.
name|ClassQueue
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
name|util
operator|.
name|ClassSet
import|;
end_import

begin_comment
comment|/**  * Find all classes referenced by given start class and all classes referenced by those and so on. In other words:  * Compute the transitive hull of classes used by a given class. This is done by checking all ConstantClass entries and  * all method and field signatures.  *<p>  * This may be useful in order to put all class files of an application into a single JAR file, e.g..  *</p>  *<p>  * It fails however in the presence of reflexive code aka introspection.  *</p>  *<p>  * You'll need Apache's regular expression library supplied together with BCEL to use this class.  *</p>  */
end_comment

begin_class
specifier|public
class|class
name|TransitiveHull
extends|extends
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|EmptyVisitor
block|{
specifier|public
specifier|static
specifier|final
name|String
index|[]
name|IGNORED
init|=
block|{
literal|"java[.].*"
block|,
literal|"javax[.].*"
block|,
literal|"sun[.].*"
block|,
literal|"sunw[.].*"
block|,
literal|"com[.]sun[.].*"
block|,
literal|"org[.]omg[.].*"
block|,
literal|"org[.]w3c[.].*"
block|,
literal|"org[.]xml[.].*"
block|,
literal|"net[.]jini[.].*"
block|}
decl_stmt|;
specifier|public
specifier|static
name|void
name|main
parameter_list|(
specifier|final
name|String
index|[]
name|argv
parameter_list|)
block|{
name|JavaClass
name|java_class
decl_stmt|;
try|try
block|{
if|if
condition|(
name|argv
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"transitive: No input files specified"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|(
name|java_class
operator|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|argv
index|[
literal|0
index|]
argument_list|)
operator|)
operator|==
literal|null
condition|)
block|{
name|java_class
operator|=
operator|new
name|ClassParser
argument_list|(
name|argv
index|[
literal|0
index|]
argument_list|)
operator|.
name|parse
argument_list|()
expr_stmt|;
block|}
specifier|final
name|TransitiveHull
name|hull
init|=
operator|new
name|TransitiveHull
argument_list|(
name|java_class
argument_list|)
decl_stmt|;
name|hull
operator|.
name|start
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|hull
operator|.
name|getClassNames
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
specifier|final
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
specifier|private
specifier|final
name|ClassQueue
name|queue
decl_stmt|;
specifier|private
specifier|final
name|ClassSet
name|set
decl_stmt|;
specifier|private
name|ConstantPool
name|cp
decl_stmt|;
specifier|private
name|String
index|[]
name|ignored
init|=
name|IGNORED
decl_stmt|;
specifier|public
name|TransitiveHull
parameter_list|(
specifier|final
name|JavaClass
name|clazz
parameter_list|)
block|{
name|queue
operator|=
operator|new
name|ClassQueue
argument_list|()
expr_stmt|;
name|queue
operator|.
name|enqueue
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
name|set
operator|=
operator|new
name|ClassSet
argument_list|()
expr_stmt|;
name|set
operator|.
name|add
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|add
parameter_list|(
name|String
name|className
parameter_list|)
block|{
name|className
operator|=
name|className
operator|.
name|replace
argument_list|(
literal|'/'
argument_list|,
literal|'.'
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|String
name|anIgnored
range|:
name|ignored
control|)
block|{
if|if
condition|(
name|Pattern
operator|.
name|matches
argument_list|(
name|anIgnored
argument_list|,
name|className
argument_list|)
condition|)
block|{
return|return;
block|}
block|}
try|try
block|{
specifier|final
name|JavaClass
name|clazz
init|=
name|Repository
operator|.
name|lookupClass
argument_list|(
name|className
argument_list|)
decl_stmt|;
if|if
condition|(
name|set
operator|.
name|add
argument_list|(
name|clazz
argument_list|)
condition|)
block|{
name|queue
operator|.
name|enqueue
argument_list|(
name|clazz
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
specifier|final
name|ClassNotFoundException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Missing class: "
operator|+
name|e
operator|.
name|toString
argument_list|()
argument_list|)
throw|;
block|}
block|}
specifier|private
name|void
name|checkType
parameter_list|(
name|Type
name|type
parameter_list|)
block|{
if|if
condition|(
name|type
operator|instanceof
name|ArrayType
condition|)
block|{
name|type
operator|=
operator|(
operator|(
name|ArrayType
operator|)
name|type
operator|)
operator|.
name|getBasicType
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|type
operator|instanceof
name|ObjectType
condition|)
block|{
name|add
argument_list|(
operator|(
operator|(
name|ObjectType
operator|)
name|type
operator|)
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
name|JavaClass
index|[]
name|getClasses
parameter_list|()
block|{
return|return
name|set
operator|.
name|toArray
argument_list|()
return|;
block|}
specifier|public
name|String
index|[]
name|getClassNames
parameter_list|()
block|{
return|return
name|set
operator|.
name|getClassNames
argument_list|()
return|;
block|}
specifier|public
name|String
index|[]
name|getIgnored
parameter_list|()
block|{
return|return
name|ignored
return|;
block|}
comment|/**      * Set the value of ignored.      *      * @param v Value to assign to ignored.      */
specifier|public
name|void
name|setIgnored
parameter_list|(
specifier|final
name|String
index|[]
name|v
parameter_list|)
block|{
name|ignored
operator|=
name|v
expr_stmt|;
block|}
comment|/**      * Start traversal using DescendingVisitor pattern.      */
specifier|public
name|void
name|start
parameter_list|()
block|{
while|while
condition|(
operator|!
name|queue
operator|.
name|empty
argument_list|()
condition|)
block|{
specifier|final
name|JavaClass
name|clazz
init|=
name|queue
operator|.
name|dequeue
argument_list|()
decl_stmt|;
name|cp
operator|=
name|clazz
operator|.
name|getConstantPool
argument_list|()
expr_stmt|;
operator|new
name|org
operator|.
name|apache
operator|.
name|bcel
operator|.
name|classfile
operator|.
name|DescendingVisitor
argument_list|(
name|clazz
argument_list|,
name|this
argument_list|)
operator|.
name|visit
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantClass
parameter_list|(
specifier|final
name|ConstantClass
name|cc
parameter_list|)
block|{
specifier|final
name|String
name|className
init|=
operator|(
name|String
operator|)
name|cc
operator|.
name|getConstantValue
argument_list|(
name|cp
argument_list|)
decl_stmt|;
name|add
argument_list|(
name|className
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantFieldref
parameter_list|(
specifier|final
name|ConstantFieldref
name|cfr
parameter_list|)
block|{
name|visitRef
argument_list|(
name|cfr
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantInterfaceMethodref
parameter_list|(
specifier|final
name|ConstantInterfaceMethodref
name|cimr
parameter_list|)
block|{
name|visitRef
argument_list|(
name|cimr
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|visitConstantMethodref
parameter_list|(
specifier|final
name|ConstantMethodref
name|cmr
parameter_list|)
block|{
name|visitRef
argument_list|(
name|cmr
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|visitRef
parameter_list|(
specifier|final
name|ConstantCP
name|ccp
parameter_list|,
specifier|final
name|boolean
name|method
parameter_list|)
block|{
specifier|final
name|String
name|className
init|=
name|ccp
operator|.
name|getClass
argument_list|(
name|cp
argument_list|)
decl_stmt|;
name|add
argument_list|(
name|className
argument_list|)
expr_stmt|;
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
name|ccp
operator|.
name|getNameAndTypeIndex
argument_list|()
argument_list|,
name|Constants
operator|.
name|CONSTANT_NameAndType
argument_list|)
decl_stmt|;
specifier|final
name|String
name|signature
init|=
name|cnat
operator|.
name|getSignature
argument_list|(
name|cp
argument_list|)
decl_stmt|;
if|if
condition|(
name|method
condition|)
block|{
specifier|final
name|Type
name|type
init|=
name|Type
operator|.
name|getReturnType
argument_list|(
name|signature
argument_list|)
decl_stmt|;
name|checkType
argument_list|(
name|type
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|Type
name|type1
range|:
name|Type
operator|.
name|getArgumentTypes
argument_list|(
name|signature
argument_list|)
control|)
block|{
name|checkType
argument_list|(
name|type1
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|checkType
argument_list|(
name|Type
operator|.
name|getType
argument_list|(
name|signature
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

