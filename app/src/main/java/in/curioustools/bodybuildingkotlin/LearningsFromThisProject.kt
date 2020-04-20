package `in`.curioustools.bodybuildingkotlin

/*

Note : 3 articles coming up :
# 1. : all content from https://fabiomsr.github.io/from-java-to-kotlin/ copied directly
# 2. : kotlin syntactic function examples(map reduce ,apply, sum, dorEach,etc)
# 3. : kotlin templates (basic recyclerview,)
# 4. : ViewBinding templates  for classes,recyclerview,alertdialogue,etc

1.
  class Exercise (val id,val name:String, val timeInSeconds:Int) {  }

  >> will go into details later,  but this syntax tries to create a class with optional params, but
     in a weird way:

        1. with java,we can only call this via Exercise e = new Exercise("id","name",999);
           There is no possibility of an optional param(There is something about @JVMOverloads
           annotation, but that would still not work with this

        2. with kotlin , we also don't have the "conventional" optional params. it will also only
           work val e =Exercise("id","name",999) and not e =Exercise("name",999)

       3. The only way it is able to provide *some* optional parameters support is when we use
          parameter name alongside its value :

          val e9 = Exercise(name = "e9",timeInSeconds = 22) //this works
          val e9 = Exercise(timeInSeconds = 22,name = "e9") //this also works, and is very cool!


          Problems with this approach : This is not a 100% effective solution:

          1. you have to provide the named parameters for all the arguements if you want the
             compiler to use default value
          2. named and positional arguement mixing is not allowed : this is not allowed:
               val e91 = Exercise(name = "e9", 24)
             but if you are passing all the arguements, then we can have a "sequential mixing" :
             i.e all the positional arguements are first followed by all the named arguements in
             any order:
               val e9 = Exercise( "id", timeInSeconds = 22,name = "e9"

         3. this is position sensitive. if default value items are in last, then compiler will
            guess them if you provide all the positional values, but if it is at first, then it won't
            work.


  UNIVERSAL SOLUTION: consists of writing extra secondary constructor:

          class Exercise (val id,val name:String, val timeInSeconds:Int) {

           constructor(name:String,timeInSeconds:Int) :
                                            this("id", name, timeInSeconds)
          }

    Features :
        1. java:
            Exercise e = new Exercise("id","name",444);  //allowed
            Exercise e = new Exercise("name",444);  //allowed

        2. kotlin:
            val e = Exercise("id","name",444);  //allowed
            val e = Exercise("name",444);  //allowed

       3. But this method does not provide the addtional feature of mixed named arguements in the
          java side of code( for kotlin, it still provides that) +its still a lot of code. that's
          why people prefer @JVMOverride annotation. in summary :

          1. if you want to have the complete ability to avoid providing a param, go for constructor
          overloading

          2. if you want the power to send params in mixed, named order with limited ability to avoid
          passing a particular param, go for default arguement

          3. if you want the same thing as (2) for both java and kotlin, provide an @JVMOverloading
          implementation
====================================================================================================
MAKING A CLASS PARCELLABLE
https://medium.com/the-lazy-coders-journal/easy-parcelable-in-kotlin-the-lazy-coders-way-9683122f4c00

IN SUMMARY : simply implement parcelable and anotate with @Parcelize
@Parcelize
class Exercise (val id,val name:String, val timeInSeconds:Int) : Parcelable{ ... }


====================================================================================================
Static functions:
here we make companion object . jis tarah java me call krte the uss tarah :
1. kotlin me call krna ho : do nothing except Classname.function()
2. java me call krna ho : do nothing except Classname.Companion.function()
eg: to|do
====================================================================================================
KOTLIN LIST
these are immutable by default. idk how to use them , but they break most of the java list
functionality  you should be  mutable list &/or arraylist most of the time
====================================================================================================
INTENT FEATURES:

java: MainActivity.this     == this@MainActivity              :     kotlin
java: SplashActivity.class  == SplashActivity::class.java     :     kotlin

====================================================================================================

aonymous classes are made by implementing them to "object"

InterfaceName n = new ABCClass.InterfaceName() {
        @Override
        public int function (int arg) {...}
       }

val  n = object : InterfaceName() {
            override fun function(arg: Int): Int {}
        }
====================================================================================================

Getters And Setters (Cool Ways)
kotlin provides some really cool ways to create getters and setters

private var dataList = listOf("any,"mary","suzy")
        get() { return field }
        set(value) { field = value;notifyDataSetChanged() }

since oneline functions can be written in even smaller (inline) notation, getter becomes even more simpler

private var dataList = listOf("any,"mary","suzy")
        get() =field
        set(value) { field = value;notifyDataSetChanged() }

--------------------------------------------------------------

note its worth mentioning that these kind of listeners are already available to every
"pseudo private variable" .I say "pseudo private" because every `val x=something` is generated as
`private final x=something;` with a default getter ( and var with both getters and setters).
These are merely to update those default definitions to include any extra transformation/operations
Because the default getters and setters are just like this:
public int getX(){return x; }
public void setX(int x){this.x= x;}
These are the kind of  getters and setters that are already available to all variables

====================================================================================================

SYNTHETIC BINDINGS SUCK . i thought they would work in onstart, but fuck no
use view binding, that's the best binding library out there, that too by google
(synthetic binding was added by jetbrains). would cover more details sometime later
https://developer.android.com/topic/libraries/view-binding#kotlin
I am currently trying recycler view with view binding

====================================================================================================
TRY TO NEVER USE `lateinit var x` or `var x:Class!` ;

var x:class = hastobeSomething
var x:class? //doesn't has to be something by default its null, but you will always have to check its nullability when used
lateinit var x; //you are back in java:|


using lateinit is equivalent to using  making a `private ClassName obj;`variable. could be null
could be not. But compiler won't tell you because it trusts you to handle it. its definition goes
like this:
"The lateinit keyword stands for late initialization. Lateinit comes very handy when a non-null
initializer cannot be supplied in the constructor, but the developer is certain that the
variable will not be null when accessing it, thus avoiding null checks when referencing it later."

unfortunately we have to either use this or always use !!/? annotation

sme goes with val x:Class! . compiler will just keep a silence on ever warning

====================================================================================================
the inner classes when created in a usual way in kotlin, are static by default . Thus it is said to
make them non static by using "inner" keyword
====================================================================================================
i am going to make a recycler view emplate for both a simple recycler view and the one with
viewbinding. view binding is very good!(although useless i must say)
====================================================================================================

kotlin mapping and reducing functions could be of great use. like if i have to take a sum of all
the marks of boys in a list:

   val boysList= Boy.getRandom5Boyss();
    println(exerList)               //[boy(ram,m,5) ,boy(ram2,m,2) ,boy(ram3,m,3) ,boy(ram4,m,4) ]

    var sum1 = 0;
    boysList.forEach{sum1+=it.mks}

    val sum2 = boysList.map { it->it.mks }.sum()
    val sum3 = boysList.map { it.mks }.sum()  // arrow can be completely removed

    println("$sum1 $sum2 $sum3") //14 14 14 // sum of mks

i will be making an template file on this and other use case

====================================================================================================

*/
