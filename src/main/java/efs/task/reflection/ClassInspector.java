package efs.task.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ClassInspector {

  /**
   * Metoda powinna wyszukać we wszystkich zadeklarowanych przez klasę polach te które oznaczone
   * są adnotacją podaną jako drugi parametr wywołania tej metody. Wynik powinien zawierać tylko
   * unikalne nazwy pól (bez powtórzeń).
   *
   * @param type       klasa (typ) poddawana analizie
   * @param annotation szukana adnotacja
   * @return lista zawierająca tylko unikalne nazwy pól oznaczonych adnotacją
   */
  public static Collection<String> getAnnotatedFields(final Class<?> type,
      final Class<? extends Annotation> annotation) {

    List<String> uFields = new ArrayList<>();

    Field[] fields = type.getDeclaredFields();
    for(Field field : fields){
      if(field.isAnnotationPresent(annotation)){
        if(!uFields.contains(field.getName())){
          uFields.add(field.getName());
        }
      }
    }

    return uFields;

  }

  /**
   * Metoda powinna wyszukać wszystkie zadeklarowane bezpośrednio w klasie metody oraz te
   * implementowane przez nią pochodzące z interfejsów, które implementuje. Wynik powinien zawierać
   * tylko unikalne nazwy metod (bez powtórzeń).
   *
   * @param type klasa (typ) poddawany analizie
   * @return lista zawierająca tylko unikalne nazwy metod zadeklarowanych przez klasę oraz te
   * implementowane
   */
  public static Collection<String> getAllDeclaredMethods(final Class<?> type) {
    List<String> methods = new ArrayList<>();
    Method[] classMethods = type.getDeclaredMethods();
    for(var m : classMethods){
      if(!methods.contains(m.getName())){
        methods.add(m.getName());
      }
    }
    Class<?>[] implementedInterfaces = type.getInterfaces();
    for(var i : implementedInterfaces){
      Method[] interfaceMethods = i.getDeclaredMethods();
      for(var m : interfaceMethods){
        if(!methods.contains(m.getName())){
          methods.add(m.getName());
        }
      }
    }
    return methods;
  }

  /**
   * Metoda powinna odszukać konstruktor zadeklarowany w podanej klasie który przyjmuje wszystkie
   * podane parametry wejściowe. Należy tak przygotować implementację aby nawet w przypadku gdy
   * pasujący konstruktor jest prywatny udało się poprawnie utworzyć nową instancję obiektu
   * <p>
   * Przykładowe użycia:
   * <code>ClassInspector.createInstance(Villager.class)</code>
   * <code>ClassInspector.createInstance(Villager.class, "Nazwa", "Opis")</code>
   *
   * @param type klasa (typ) którego instancje ma zostać utworzona
   * @param args parametry które mają zostać przekazane do konstruktora
   * @return nowa instancja klasy podanej jako parametr zainicjalizowana podanymi parametrami
   * @throws Exception wyjątek spowodowany nie znalezieniem odpowiedniego konstruktora
   */
  public static <T> T createInstance(final Class<T> type, final Object... args) throws Exception {
    Constructor<?>[] constructors = type.getDeclaredConstructors();

    for(var constructor : constructors){
      boolean cont = true;
      Class<?>[] parameterTypes = constructor.getParameterTypes();
      if(parameterTypes.length!=args.length){
        cont = false;
      }else{
        for(int i = 0; i< parameterTypes.length; i++){
          if (!parameterTypes[i].isInstance(args[i])) {
            cont = false;
            break;
          }
        }
      }
      if(cont){
        constructor.setAccessible(true);
        return (T) constructor.newInstance(args);
      }


    }
    throw new Exception("No such constructor found");
  }
}
