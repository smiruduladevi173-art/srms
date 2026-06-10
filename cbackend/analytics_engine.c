#include <stdio.h>

int main() {

    FILE *input;
    FILE *output;

    input =
            fopen(
                    "input.txt",
                    "r"
            );

    if(input == NULL) {

        printf("Input file not found\n");

        return 1;
    }

    int mark = 0;

    int total = 0;
    int count = 0;

    int highest = -1;
    int lowest = 101;

    int passed = 0;

    while(
            fscanf(
                    input,
                    "%d",
                    &mark
            ) == 1
    ) {

        printf(
                "READ = %d\n",
                mark
        );

        total += mark;

        count++;

        if(mark > highest)
            highest = mark;

        if(mark < lowest)
            lowest = mark;

        if(mark >= 50)
            passed++;
    }

    fclose(input);

    if(count == 0) {

        printf("No marks found\n");

        return 1;
    }

    float average =
            (float) total / count;

    float passPercentage =
            ((float) passed / count)
                    * 100;

    output =
            fopen(
                    "output.txt",
                    "w"
            );

    if(output == NULL) {

        printf("Cannot create output\n");

        return 1;
    }

    fprintf(
            output,
            "AVERAGE=%.2f\n",
            average
    );

    fprintf(
            output,
            "PASS_PERCENT=%.2f\n",
            passPercentage
    );

    fprintf(
            output,
            "HIGHEST=%d\n",
            highest
    );

    fprintf(
            output,
            "LOWEST=%d\n",
            lowest
    );

    fclose(output);

    printf(
            "FINAL HIGHEST = %d\n",
            highest
    );

    return 0;
}