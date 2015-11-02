% INPUT DESCRIPTION
% music: the local filepath of a .wav file to be read
% n: the number of standard deviations away from the mean needed to be
%   considered a musical feature

% OUTPUT DESCRIPTION
% peaks: the height of each peak found, not stripped of noise
% loc: the location (in samples) of each peak found, not stripped of noise
% loc_cancelled_trimmed: the location (samples) of each peak, stripped of
%   noise and trimmed to the last peak
% loc_sec: the location (seconds) of each peak, stripped of noise

% NOTE: in the context of this function, 'noise' is the replication of a
%   feature in close proximity to its origin due to signal bouncing

function [peaks, loc, loc_cancelled_trimmed, loc_sec_trimmed] = musicalfeatures(music, n)

% arbitrary constant (in samples)
%   - seems to be good at grabbing nearby replications

NOISE_CANCELLATION_OFFSET = 100;

% generating initial peaks and loc vectors (with no noise cancellation)

[wav,fs] = audioread(music);
left_channel = wav(:,1);
len = size(left_channel);
for i=1:len
    left_channel(i) = abs(left_channel(i));
end
standard_deviation = std(left_channel);
avg = mean(left_channel);
threshold = avg+(standard_deviation*n);
[peaks, loc] = findpeaks(left_channel, 'MinPeakHeight', threshold);
findpeaks(left_channel, 'MinPeakHeight', threshold);

% noise cancellation snippet
% does not record any peak within NOISE_CANCELLATION_OFFEST samples 
%   of anything previous

[h,w] = size(loc);
loc_cancelled = zeros(h,1);
for i=1:size(loc)
    loc_cancelled(i) = -1;
end
for i=1:size(loc)
    curr_peak = loc(i);
    can_add = true;
    store_index = -1;
    for j=1:size(loc_cancelled)
        if curr_peak < (loc_cancelled(j) + NOISE_CANCELLATION_OFFSET)
            can_add = false;
        end
        if loc_cancelled(j) == -1
            store_index = j;
            break;
        end
    end
    if can_add == true
        loc_cancelled(store_index) = curr_peak;
    end
end

loc_cancelled_trimmed = zeros(store_index-1,1);
for i=1:store_index-1
    loc_cancelled_trimmed(i) = loc_cancelled(i);
end
    
% converting location of peaks from samples to seconds

[h,w] = size(loc_cancelled_trimmed);
loc_sec_trimmed = zeros(h,1);
for i=1:size(loc_cancelled_trimmed)
    loc_sec_trimmed(i) = loc_cancelled(i)/fs;
end