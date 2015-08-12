begin_unit|revision:1.0.0;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Licensed to the Apache Software Foundation (ASF) under one or more  * contributor license agreements.  See the NOTICE file distributed with  * this work for additional information regarding copyright ownership.  * The ASF licenses this file to You under the Apache License, Version 2.0  * (the "License"); you may not use this file except in compliance with  * the License.  You may obtain a copy of the License at  *  *      http://www.apache.org/licenses/LICENSE-2.0  *  *  Unless required by applicable law or agreed to in writing, software  *  distributed under the License is distributed on an "AS IS" BASIS,  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *  See the License for the specific language governing permissions and  *  limitations under the License.  *  */
end_comment

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
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
name|commons
operator|.
name|bcel6
operator|.
name|classfile
operator|.
name|Utility
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|ALOAD
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|ClassGen
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|ConstantPoolGen
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|GETSTATIC
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|INVOKEVIRTUAL
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|InstructionConstants
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|InstructionFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|InstructionList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|MethodGen
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
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
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|PUSH
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|bcel6
operator|.
name|generic
operator|.
name|Type
import|;
end_import

begin_comment
comment|/**  * Dynamically creates and uses a proxy for {@code java.awt.event.ActionListener}  * via the classloader mechanism if called with  *<pre>java org.apache.commons.bcel6.util.JavaWrapper ProxyCreator</pre>  *  * The trick is to encode the byte code we need into the class name  * using the Utility.encode() method. This will result however in big  * ugly class name, so for many cases it will be more sufficient to  * put some clever creation code into the class loader.<br> This is  * comparable to the mechanism provided via  * {@code java.lang.reflect.Proxy}, but much more flexible.  *  * @version $Id$  * @see org.apache.commons.bcel6.util.JavaWrapper  * @see org.apache.commons.bcel6.util.ClassLoader  * @see Utility  */
end_comment

begin_class
specifier|public
class|class
name|ProxyCreator
block|{
comment|/**      * Load class and create instance      */
specifier|public
specifier|static
name|Object
name|createProxy
parameter_list|(
name|String
name|pack
parameter_list|,
name|String
name|class_name
parameter_list|)
block|{
try|try
block|{
name|Class
argument_list|<
name|?
argument_list|>
name|cl
init|=
name|Class
operator|.
name|forName
argument_list|(
name|pack
operator|+
literal|"$$BCEL$$"
operator|+
name|class_name
argument_list|)
decl_stmt|;
return|return
name|cl
operator|.
name|newInstance
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
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
return|return
literal|null
return|;
block|}
comment|/**      * Create JavaClass object for a simple proxy for an java.awt.event.ActionListener      * that just prints the passed arguments, load and use it via the class loader      * mechanism.      */
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|argv
parameter_list|)
throws|throws
name|Exception
block|{
name|ClassLoader
name|loader
init|=
name|ProxyCreator
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
decl_stmt|;
comment|// instanceof won't work here ...
if|if
condition|(
name|loader
operator|.
name|getClass
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
literal|"class org.apache.commons.bcel6.util.ClassLoader"
argument_list|)
condition|)
block|{
comment|// Real class name will be set by the class loader
name|ClassGen
name|cg
init|=
operator|new
name|ClassGen
argument_list|(
literal|"foo"
argument_list|,
literal|"java.lang.Object"
argument_list|,
literal|""
argument_list|,
name|Constants
operator|.
name|ACC_PUBLIC
argument_list|,
operator|new
name|String
index|[]
block|{
literal|"java.awt.event.ActionListener"
block|}
argument_list|)
decl_stmt|;
comment|// That's important, otherwise newInstance() won't work
name|cg
operator|.
name|addEmptyConstructor
argument_list|(
name|Constants
operator|.
name|ACC_PUBLIC
argument_list|)
expr_stmt|;
name|InstructionList
name|il
init|=
operator|new
name|InstructionList
argument_list|()
decl_stmt|;
name|ConstantPoolGen
name|cp
init|=
name|cg
operator|.
name|getConstantPool
argument_list|()
decl_stmt|;
name|InstructionFactory
name|factory
init|=
operator|new
name|InstructionFactory
argument_list|(
name|cg
argument_list|)
decl_stmt|;
name|int
name|out
init|=
name|cp
operator|.
name|addFieldref
argument_list|(
literal|"java.lang.System"
argument_list|,
literal|"out"
argument_list|,
literal|"Ljava/io/PrintStream;"
argument_list|)
decl_stmt|;
name|int
name|println
init|=
name|cp
operator|.
name|addMethodref
argument_list|(
literal|"java.io.PrintStream"
argument_list|,
literal|"println"
argument_list|,
literal|"(Ljava/lang/Object;)V"
argument_list|)
decl_stmt|;
name|MethodGen
name|mg
init|=
operator|new
name|MethodGen
argument_list|(
name|Constants
operator|.
name|ACC_PUBLIC
argument_list|,
name|Type
operator|.
name|VOID
argument_list|,
operator|new
name|Type
index|[]
block|{
operator|new
name|ObjectType
argument_list|(
literal|"java.awt.event.ActionEvent"
argument_list|)
block|}
argument_list|,
literal|null
argument_list|,
literal|"actionPerformed"
argument_list|,
literal|"foo"
argument_list|,
name|il
argument_list|,
name|cp
argument_list|)
decl_stmt|;
comment|// System.out.println("actionPerformed:" + event);
name|il
operator|.
name|append
argument_list|(
operator|new
name|GETSTATIC
argument_list|(
name|out
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|factory
operator|.
name|createNew
argument_list|(
literal|"java.lang.StringBuffer"
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionConstants
operator|.
name|DUP
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|PUSH
argument_list|(
name|cp
argument_list|,
literal|"actionPerformed:"
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|factory
operator|.
name|createInvoke
argument_list|(
literal|"java.lang.StringBuffer"
argument_list|,
literal|"<init>"
argument_list|,
name|Type
operator|.
name|VOID
argument_list|,
operator|new
name|Type
index|[]
block|{
name|Type
operator|.
name|STRING
block|}
argument_list|,
name|Constants
operator|.
name|INVOKESPECIAL
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|ALOAD
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|factory
operator|.
name|createAppend
argument_list|(
name|Type
operator|.
name|OBJECT
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
operator|new
name|INVOKEVIRTUAL
argument_list|(
name|println
argument_list|)
argument_list|)
expr_stmt|;
name|il
operator|.
name|append
argument_list|(
name|InstructionConstants
operator|.
name|RETURN
argument_list|)
expr_stmt|;
name|mg
operator|.
name|stripAttributes
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|mg
operator|.
name|setMaxStack
argument_list|()
expr_stmt|;
name|mg
operator|.
name|setMaxLocals
argument_list|()
expr_stmt|;
name|cg
operator|.
name|addMethod
argument_list|(
name|mg
operator|.
name|getMethod
argument_list|()
argument_list|)
expr_stmt|;
name|byte
index|[]
name|bytes
init|=
name|cg
operator|.
name|getJavaClass
argument_list|()
operator|.
name|getBytes
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Uncompressed class: "
operator|+
name|bytes
operator|.
name|length
argument_list|)
expr_stmt|;
name|String
name|s
init|=
name|Utility
operator|.
name|encode
argument_list|(
name|bytes
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Encoded class: "
operator|+
name|s
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"Creating proxy ... "
argument_list|)
expr_stmt|;
name|ActionListener
name|a
init|=
operator|(
name|ActionListener
operator|)
name|createProxy
argument_list|(
literal|"foo.bar."
argument_list|,
name|s
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Done. Now calling actionPerformed()"
argument_list|)
expr_stmt|;
name|a
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|a
argument_list|,
name|ActionEvent
operator|.
name|ACTION_PERFORMED
argument_list|,
literal|"hello"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Call me with java org.apache.commons.bcel6.util.JavaWrapper ProxyCreator"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

