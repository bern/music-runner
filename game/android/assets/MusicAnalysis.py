import sys

import numpy as np
import scipy.io.wavfile

def analyze():
	if(len(sys.argv) == 1):
		return -1

	rate, data = scipy.io.wavfile.read(sys.argv[1])

	numFrames = len(data)

	print(numFrames);

	dataToAnalyze = abs(data[:,0])

	std = np.std(dataToAnalyze)

	#print(std)

	frameVec = []
	featureVal = []
	densityVec = []

	thousandMark = -1
	thousandDensity = -1

	#print(std)

	for x in range(0, len(dataToAnalyze)):
		if(dataToAnalyze[x] >= (std*3)):
			if((thousandMark < 0) or (x > (thousandMark + 1000))):
				if(thousandDensity >= 0):
					densityVec.append(thousandDensity)
				thousandMark = x;
				frameVec.append(x)
				featureVal.append(dataToAnalyze[x])
				thousandDensity = 0
			elif (x <= (thousandMark + 1000)):
				thousandDensity += 1
		if(x % np.floor(len(dataToAnalyze)/10) == 0):
			percentComplete = ((x/np.floor(len(dataToAnalyze)/10))*10)
			#print(percentComplete)
	if(len(densityVec) < len(frameVec)):
		densityVec.append(thousandDensity)

	for x in range(0, len(frameVec)-1):
	    print(frameVec[x])
	    #print(featureVal[x])
	    print(densityVec[x])

	return numFrames

analyze()