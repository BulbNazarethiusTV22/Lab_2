# Lab_2

Програма демонструє паралельну обробку масиву за допомогою пулу потоків. Користувач вводить мінімальне і максимальне значення для генерації випадкових чисел у масиві, а також множник, яким ці числа будуть збільшені. Масив випадкових чисел ділиться на частини (по 10 елементів), і кожна частина обробляється окремим потоком.

Результати зібрані у потокобезпечний список CopyOnWriteArrayList, а після завершення обробки всі частини об’єднуються у фінальний масив. Програма вимірює і виводить загальний час виконання.

Асинхронний підхід забезпечує ефективність обробки навіть для великих масивів, дозволяючи виконувати обчислення паралельно.
