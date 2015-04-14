#!/usr/bin/python2

import sys
import os
import os.path
import mutagen.mp3

def main(argv):
    sum = 0
    target = int(argv[1])/8
    for root, dirs, files in os.walk(argv[0]):
        for f in files:
            name, ext = os.path.splitext(f)
            if(ext.lower() == ".mp3"):
                p = os.path.join(root, f)
                absp = os.path.abspath(p)
                audio = mutagen.mp3.MP3(absp)
                length = audio.info.length
                bitrate = (audio.info.bitrate/8000)
                if(bitrate > target):
                    sum += length * target
                else:
                    sum += length * bitrate

    print round(sum/1024, 2), "MB", "or", round(sum/(1024*1024), 2), "GB" 

if __name__ == "__main__":
   main(sys.argv[1:])
