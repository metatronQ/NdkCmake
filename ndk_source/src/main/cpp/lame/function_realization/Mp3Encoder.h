//
// Created by bjy on 2022/1/20.
//

#ifndef NDKCMAKE_MP3ENCODER_H
#define NDKCMAKE_MP3ENCODER_H


#include <cstdio>
#include "../libmp3lame/lame.h"

class Mp3Encoder {
private:
    FILE *pcmFile;
    FILE *mp3File;
    lame_t lameClient;
public:
    Mp3Encoder();

    ~Mp3Encoder();

    int Init(const char *pcmFilePath, const char *mp3FilePath, int sampleRate, int channels,
             int bitRate);

    void Encode();

    void Destory();

};


#endif //NDKCMAKE_MP3ENCODER_H
