import org.apache.bcel._
import org.apache.bcel.generic._
import org.apache.bcel.Constants._

object Main extends App{
  
val cg = new ClassGen("HelloBCEL", "java.lang.Object", // Class, superclass
  "(no filename)", ACC_PUBLIC | ACC_SUPER, null)
val cp = cg.getConstantPool() // cg creates constant pool
val il = new InstructionList()
val factory = new InstructionFactory(cg)

cg.addEmptyConstructor(ACC_PUBLIC) // if no constructor provided
cg.getJavaClass().dump("HelloBCEL.class")

val mg = new MethodGen(ACC_STATIC | ACC_PUBLIC, // access flags
  Type.VOID, // return type
  Array(new ArrayType(Type.STRING, 1)), // argument types
  Array("argv"), // arg names
  "main", "HelloBCEL", // method, class
  il, cp) 


il.append(factory.createFieldAccess("java.lang.System", "out", 
  new ObjectType("java.io.PrintStream"),
  Constants.GETSTATIC))
  
il.append(factory.createConstant("Hello, World!"))

il.append(factory.createInvoke("java.io.PrintStream", "println",
  Type.VOID, Array(Type.STRING), // return type, param types
  Constants.INVOKEVIRTUAL))
  
il.append(InstructionFactory.createReturn(Type.VOID))
mg.setMaxStack()
cg.addMethod(mg.getMethod())
il.dispose() // Allow instruction handles to be reused

}