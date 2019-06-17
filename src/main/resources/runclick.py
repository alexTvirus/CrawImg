import sys
from pymouse import PyMouse

m = PyMouse()
m.click(sys.argv[1],sys.argv[2])
print ("This is the name of the script: ", sys.argv[1]+" "+sys.argv[2])