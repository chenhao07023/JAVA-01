import java.io.*;
import java.lang.reflect.*;

public class MyLoader extends ClassLoader {

    public static void main(String[] args) {

        try {
            MyLoader myClassLoader = new MyLoader();
            Class<?> Hello = myClassLoader.loadClass("Hello");
            System.out.println("loader:" + Hello.getClassLoader());

            Method method = Hello.getDeclaredMethod("hello", null);
            method.invoke(Hello.newInstance(),  new Object[]{});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class hello = null;
        byte[] classData = decode("Hello.xlass");
        if (classData != null) {
            hello = defineClass(name, classData, 0, classData.length);
        }
        return hello;
    }
 
    private byte[] decode(String filename) {
        byte[] b = null;
        try {
			InputStream is = new FileInputStream(filename);
            int size = is.available();
            b = new byte[size];
			int i = 0;
			int index = 0;
			while ((i = is.read())!=-1) {
				b[index] = (byte) (255-i);
				index++;
			}
			is.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
    }

}