import numpy as np
import scipy.io.wavfile

def analyze():
	rate, data = scipy.io.wavfile.read('test3.wav')

	numFrames = len(data)
	print(numFrames)
	return numFrames

analyze()