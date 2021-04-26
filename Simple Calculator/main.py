# Importing the needed modules
from tkinter import *
import parser
from math import factorial


# Receiving the digit as parameter and display it on the input field
def get_variables(num, display):
    global tracker
    tracker = len(display.get())
    if display.get() == 'Error':
        clear_all(display)
    display.insert(tracker, num)
    tracker += len(str(num))


# Calculating function scans the string to evaluates and display it
def calculate(display):
    global tracker
    operation = str(display.get())
    # The correct operators assigned to the ones used to display to evaluate them without errors.
    special_operations = {'^': '**', 'x': '*', '_': '-', '÷': '/'}
    try:
        # This loop iterate the previous dictionary to correct the operators.
        for k, v in special_operations.items():
            operation = operation.replace(k, v)
        a = parser.expr(operation).compile()
        result = eval(a)
        # Removing the redundant 0 after the dot.
        if result % 1 == 0:
            result = int(result)
        clear_all(display)
        display.insert(0, result)
    except (AttributeError, NameError, SyntaxError, ZeroDivisionError):
        clear_all(display)
        display.insert(0, "Error")


# Function which takes operator as input and displays it on the input field
def get_operation(operator, display):
    global tracker
    tracker = len(display.get())
    display.insert(tracker, operator)
    tracker += len(operator)


# Function to clear the input field
def clear_all(display):
    global tracker
    display.delete(0, END)
    tracker = 0


# Function which works like backspace
def backspace(display):
    global tracker
    operation = display.get()
    if operation == 'Error':
        clear_all(display)
    elif len(operation):
        new_string = operation[:-1]
        clear_all(display)
        display.insert(0, new_string)
        tracker -= 1
    else:
        clear_all(display)
        display.insert(0, "Error")


# Function to calculate the factorial and display it
def fact(display):
    entire_string = display.get()
    try:
        result = factorial(int(entire_string))
        clear_all(display)
        display.insert(0, result)
    except (AttributeError, NameError, SyntaxError):
        clear_all(display)
        display.insert(0, "Error")


# --------------------------------------UI Design ---------------------------------------------
def gui():
    # Creating the main window of the app
    root = Tk()
    root.title("Mahmoud's Calculator")

    # Maximum and minimum size of windows
    root.minsize(300, 300)
    root.maxsize(400, 500)

    # adding the input field
    display = Entry(root)
    display.grid(row=1, columnspan=6, sticky=N + S + E + W)

    # Add numbers buttons to the window.
    button_7 = Button(root, text="7", command=lambda: get_variables(7, display))  # First row
    button_7.grid(row=2, column=0, sticky=N + S + E + W)
    button_8 = Button(root, text="8", command=lambda: get_variables(8, display))  # First row
    button_8.grid(row=2, column=1, sticky=N + S + E + W)
    button_9 = Button(root, text="9", command=lambda: get_variables(9, display))  # First row
    button_9.grid(row=2, column=2, sticky=N + S + E + W)
    button_4 = Button(root, text="4", command=lambda: get_variables(4, display))  # Second row
    button_4.grid(row=3, column=0, sticky=N + S + E + W)
    button_5 = Button(root, text="5", command=lambda: get_variables(5, display))  # Second row
    button_5.grid(row=3, column=1, sticky=N + S + E + W)
    button_6 = Button(root, text="6", command=lambda: get_variables(6, display))  # Second row
    button_6.grid(row=3, column=2, sticky=N + S + E + W)
    button_1 = Button(root, text="1", command=lambda: get_variables(1, display))  # Third row
    button_1.grid(row=4, column=0, sticky=N + S + E + W)
    button_2 = Button(root, text="2", command=lambda: get_variables(2, display))  # Third row
    button_2.grid(row=4, column=1, sticky=N + S + E + W)
    button_3 = Button(root, text="3", command=lambda: get_variables(3, display))  # Third row
    button_3.grid(row=4, column=2, sticky=N + S + E + W)
    button_0 = Button(root, text="0", command=lambda: get_variables(0, display))  # Fourth row
    button_0.grid(row=5, column=1, sticky=N + S + E + W)

    # Adding operators buttons to the window.
    plus_button = Button(root, text="+", command=lambda: get_operation("+", display))
    plus_button.grid(row=2, column=3, sticky=N + S + E + W)
    minus_button = Button(root, text="_", command=lambda: get_operation("-", display))
    minus_button.grid(row=3, column=3, sticky=N + S + E + W)
    mult_button = Button(root, text="x", command=lambda: get_operation("x", display))
    mult_button.grid(row=4, column=3, sticky=N + S + E + W)
    dev_button = Button(root, text="÷", command=lambda: get_operation("÷", display))
    dev_button.grid(row=5, column=3, sticky=N + S + E + W)
    mod_button = Button(root, text="%", command=lambda: get_operation("%", display))
    mod_button.grid(row=3, column=4, sticky=N + S + E + W)
    exp_button = Button(root, text="exp", command=lambda: get_operation("**2", display))
    exp_button.grid(row=5, column=4, sticky=N + S + E + W)
    del_button = Button(root, text="del", command=lambda: backspace(display))
    del_button.grid(row=2, column=5, sticky=N + S + E + W)
    fact_button = Button(root, text="X!", command=lambda: fact(display))
    fact_button.grid(row=3, column=5, sticky=N + S + E + W)
    pow_button = Button(root, text="pow", command=lambda: get_operation("^", display))
    pow_button.grid(row=5, column=5, sticky=N + S + E + W)
    equal_button = Button(root, text="=", command=lambda: calculate(display))
    equal_button.grid(columnspan=6, sticky=N + S + E + W)

    # Adding other buttons to Increase the calculator functionality.
    pi_button = Button(root, text="pi", command=lambda: get_operation("*3.14", display))
    pi_button.grid(row=5, column=0, sticky=N + S + E + W)
    ac_button = Button(root, text="AC", command=lambda: clear_all(display))
    ac_button.grid(row=2, column=4, sticky=N + S + E + W)
    dot_button = Button(root, text=".", command=lambda: get_variables(".", display))
    dot_button.grid(row=5, column=2, sticky=N + S + E + W)
    left_bracket = Button(root, text="(", command=lambda: get_operation("(", display))
    left_bracket.grid(row=4, column=4, sticky=N + S + E + W)
    right_brackets = Button(root, text=")", command=lambda: get_operation(")", display))
    right_brackets.grid(row=4, column=5, sticky=N + S + E + W)

    # Configuration to the buttons size in case that the user resize the window
    Grid.columnconfigure(root, 0, weight=1)
    for i in range(1, 6):
        Grid.rowconfigure(root, i, weight=1)
        Grid.columnconfigure(root, i, weight=1)

    root.mainloop()


if __name__ == '__main__':
    # It keeps the track of current position on the input text field.
    tracker = 0
    gui()
