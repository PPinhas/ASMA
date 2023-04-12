import subprocess
import os
# Define the arguments to pass to the Java program
args = ["arg1", "arg2", "arg3", "arg4", "arg5"]

# Define the path to the Java program

curr_wd = str(os.getcwd())
java_program_path = curr_wd + "/Intrigue/src"

# Define a function to run the Java program with a specific argument
def run_java_program(arg):
    subprocess.call(["java", "-cp", ".", "Main"], cwd=java_program_path)

# Run the Java program with each argument
for arg in args:
    print(java_program_path)
    run_java_program(arg)
