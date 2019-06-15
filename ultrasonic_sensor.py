import socket
import sys
import time
import threading
import RPi.GPIO as GPIO

# pip install gpio

GPIO.setmode(GPIO.BCM)

global GPIO_TRIGGER
global GPIO_ECHO


def distance():
    global GPIO_TRIGGER
    global GPIO_ECHO

    GPIO.output(GPIO_TRIGGER, True)

    time.sleep(0.00001)
    GPIO.output(GPIO_TRIGGER, False)

    start_time = time.time()
    stop_time = time.time()

    while GPIO.input(GPIO_ECHO) == 0:
        start_time = time.time()

    while GPIO.input(GPIO_ECHO) == 1:
        stop_time = time.time()

    time_elapsed = stop_time - start_time
    distance = (time_elapsed * 34300) / 2

    return distance


global live
live = True
global timeout
global delay
delay = 1.0
sender = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
receiver = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)


def send(addr, port):
    global live
    global delay
    global timeout
    timeout = 10
    while live:
        sender.sendto(str(distance()).encode('utf-8'), (addr, port))
        print("send msg")
        time.sleep(delay)
        timeout = timeout - 1
        if timeout < 1:
            live = False
    GPIO.cleanup()


def receive():
    global live
    global delay
    global timeout
    print("start receive")
    while live:
        data, _ = receiver.recvfrom(1024)
        delay = float(data.decode('utf-8'))
        timeout = 10


if __name__ == '__main__':
    if len(sys.argv) >= 5:
        addr = sys.argv[1]
        sendPort = int(sys.argv[2])
        receivePort = int(sys.argv[3])
        GPIO_TRIGGER = int(sys.argv[4])
        GPIO_ECHO = int(sys.argv[5])
    else:
        addr = "127.0.0.1"
        sendPort = 25565
        receivePort = 25566
        GPIO_TRIGGER = 17
        GPIO_ECHO = 18

    print(addr)
    print(sendPort)
    print(receivePort)
    GPIO.setup(GPIO_TRIGGER, GPIO.OUT)
    GPIO.setup(GPIO_ECHO, GPIO.IN)
    receiver.bind(('127.0.0.1', receivePort))
    threadSend = threading.Thread(target=send, args=(addr, sendPort))
    threadReceive = threading.Thread(target=receive)
    threadSend.start()
    threadReceive.start()