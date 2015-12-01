import numpy as np
import scipy.io.wavfile

def analyze():
	rate, data = scipy.io.wavfile.read('test3.wav')

	numFrames = len(data)

	dataToAnalyze = abs(data[:,0])

	std = np.std(dataToAnalyze)

	frameVec = []
	densityVec = []

	thousandMark = -1
	thousandDensity = -1

	for x in range(0, len(dataToAnalyze)):
		if(dataToAnalyze[x] >= (std*3)):
			if((thousandMark < 0) or (x > (thousandMark + 1000))):
				if(thousandDensity >= 0):
					densityVec.append(thousandDensity)
				thousandMark = x;
				frameVec.append(x)
				thousandDensity = 0
			elif (x <= (thousandMark + 1000)):
				thousandDensity += 1
		if(x % np.floor(len(dataToAnalyze)/10) == 0):
			percentComplete = ((x/np.floor(len(dataToAnalyze)/10))*10)
			#print(percentComplete)

	for x in range(0, len(data)):
	    print(frameVec[x])
	    print(densityVec[x])

	return numFrames

analyze()