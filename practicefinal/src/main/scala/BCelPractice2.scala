
import org.apache.bcel._
import org.apache.bcel.generic._
import org.apache.bcel.Constants._

object BCelPractice2 extends App{

//prepare generators, factories, etc
val cg = new ClassGen("Bits", "java.lang.Object", // Class, superclass
  "(no filename)", ACC_PUBLIC | ACC_SUPER, null)
val cp = cg.getConstantPool() // cg creates constant pool
val il = new InstructionList()
val factory = new InstructionFactory(cg)  

//write a method
val mg = new MethodGen(ACC_STATIC | ACC_PUBLIC, // access flags
Type.VOID, // return type
Array(new ArrayType(Type.STRING, 1)), // argument types
Array("argv"), // arg names
"main", "Bits", // method, class
il, cp)    

//add implementation
il.append(factory.createFieldAccess("java.lang.System", "out", 
  new ObjectType("java.io.PrintStream"),
  Constants.GETSTATIC))
  
//push a string
il.append(factory.createConstant(42))

//invoke a method
il.append(factory.createInvoke("Bits", "bits",
  Type.INT, Array(Type.INT), // return type, param types
  Constants.INVOKESTATIC))

//invoke a method
il.append(factory.createInvoke("java.io.PrintStream", "println",
  Type.VOID, Array(Type.INT), // return type, param types
  Constants.INVOKEVIRTUAL))
  
//finish method
// method implementation
il.append(InstructionFactory.createReturn(Type.VOID))
mg.setMaxStack()
cg.addMethod(mg.getMethod())
il.dispose() // Allow instruction handles to be reused

//bits method
val mg2 = new MethodGen(ACC_STATIC | ACC_PUBLIC, // access flags
Type.INT, // return type
Array(Type.INT), // argument types
Array("n"), // arg names
"bits", "Bits", // method, class
il, cp)    

//translated code from byte code from javap -c bits fucntion written in java
il.append(InstructionFactory.createLoad(Type.INT, 0))


//branch
val instr = InstructionFactory.createBranchInstruction(Constants.IFNE, null)
il.append(instr)
il.append(factory.createConstant(0))

il.append(InstructionFactory.createReturn(Type.INT))

//computes n % 2
val loc = il.append(InstructionFactory.createLoad(Type.INT, 0))
instr.setTarget(loc)

il.append(factory.createConstant(2))
il.append(InstructionFactory.createBinaryOperation("%", Type.INT))

//computes n / 2
il.append(InstructionFactory.createLoad(Type.INT, 0))
il.append(factory.createConstant(2))
il.append(InstructionFactory.createBinaryOperation("/", Type.INT))


//recursively calls bits on n/2
il.append(factory.createInvoke("Bits", "bits",
  Type.INT, Array(Type.INT), // return type, param types
  Constants.INVOKESTATIC))
  
il.append(InstructionFactory.createBinaryOperation("+", Type.INT))

il.append(InstructionFactory.createReturn(Type.INT))
mg2.setMaxStack()
cg.addMethod(mg2.getMethod())
il.dispose() // Allow instruction handles to be reused

//write class file
cg.addEmptyConstructor(ACC_PUBLIC) // if no constructor provided
cg.getJavaClass().dump("Bits.class")
  
}