/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
$(document).ready(function() {

    $(".click-title").mouseenter( function(    e){
        e.preventDefault();
        this.style.cursor="pointer";
    });
    $(".click-title").mousedown( function(event){
        event.preventDefault();
    });

    // Ugly code while this script is shared among several pages
    try{
        refreshHitsPerSecond(true);
    } catch(e){}
    try{
        refreshResponseTimeOverTime(true);
    } catch(e){}
    try{
        refreshResponseTimePercentiles();
    } catch(e){}
});


var responseTimePercentilesInfos = {
        data: {"result": {"minY": 5.0, "minX": 0.0, "maxY": 6576.0, "series": [{"data": [[0.0, 5.0], [0.1, 7.0], [0.2, 10.0], [0.3, 10.0], [0.4, 11.0], [0.5, 11.0], [0.6, 11.0], [0.7, 11.0], [0.8, 11.0], [0.9, 11.0], [1.0, 11.0], [1.1, 11.0], [1.2, 12.0], [1.3, 12.0], [1.4, 12.0], [1.5, 12.0], [1.6, 12.0], [1.7, 12.0], [1.8, 12.0], [1.9, 12.0], [2.0, 12.0], [2.1, 12.0], [2.2, 12.0], [2.3, 12.0], [2.4, 12.0], [2.5, 12.0], [2.6, 12.0], [2.7, 13.0], [2.8, 13.0], [2.9, 13.0], [3.0, 13.0], [3.1, 13.0], [3.2, 13.0], [3.3, 13.0], [3.4, 13.0], [3.5, 13.0], [3.6, 13.0], [3.7, 13.0], [3.8, 13.0], [3.9, 13.0], [4.0, 13.0], [4.1, 13.0], [4.2, 13.0], [4.3, 13.0], [4.4, 13.0], [4.5, 13.0], [4.6, 13.0], [4.7, 13.0], [4.8, 13.0], [4.9, 13.0], [5.0, 13.0], [5.1, 14.0], [5.2, 14.0], [5.3, 14.0], [5.4, 14.0], [5.5, 14.0], [5.6, 14.0], [5.7, 14.0], [5.8, 14.0], [5.9, 14.0], [6.0, 14.0], [6.1, 14.0], [6.2, 14.0], [6.3, 14.0], [6.4, 14.0], [6.5, 14.0], [6.6, 14.0], [6.7, 14.0], [6.8, 14.0], [6.9, 14.0], [7.0, 14.0], [7.1, 14.0], [7.2, 14.0], [7.3, 14.0], [7.4, 14.0], [7.5, 14.0], [7.6, 14.0], [7.7, 14.0], [7.8, 14.0], [7.9, 14.0], [8.0, 14.0], [8.1, 14.0], [8.2, 15.0], [8.3, 15.0], [8.4, 15.0], [8.5, 15.0], [8.6, 15.0], [8.7, 15.0], [8.8, 15.0], [8.9, 15.0], [9.0, 15.0], [9.1, 15.0], [9.2, 15.0], [9.3, 15.0], [9.4, 15.0], [9.5, 15.0], [9.6, 15.0], [9.7, 15.0], [9.8, 15.0], [9.9, 15.0], [10.0, 15.0], [10.1, 15.0], [10.2, 15.0], [10.3, 15.0], [10.4, 15.0], [10.5, 15.0], [10.6, 15.0], [10.7, 15.0], [10.8, 15.0], [10.9, 15.0], [11.0, 15.0], [11.1, 15.0], [11.2, 15.0], [11.3, 15.0], [11.4, 15.0], [11.5, 15.0], [11.6, 15.0], [11.7, 15.0], [11.8, 15.0], [11.9, 16.0], [12.0, 16.0], [12.1, 16.0], [12.2, 16.0], [12.3, 16.0], [12.4, 16.0], [12.5, 16.0], [12.6, 16.0], [12.7, 16.0], [12.8, 16.0], [12.9, 16.0], [13.0, 16.0], [13.1, 16.0], [13.2, 16.0], [13.3, 16.0], [13.4, 16.0], [13.5, 16.0], [13.6, 16.0], [13.7, 16.0], [13.8, 16.0], [13.9, 16.0], [14.0, 16.0], [14.1, 16.0], [14.2, 16.0], [14.3, 16.0], [14.4, 16.0], [14.5, 16.0], [14.6, 16.0], [14.7, 16.0], [14.8, 16.0], [14.9, 16.0], [15.0, 16.0], [15.1, 16.0], [15.2, 16.0], [15.3, 16.0], [15.4, 16.0], [15.5, 16.0], [15.6, 16.0], [15.7, 16.0], [15.8, 16.0], [15.9, 16.0], [16.0, 17.0], [16.1, 17.0], [16.2, 17.0], [16.3, 17.0], [16.4, 17.0], [16.5, 17.0], [16.6, 17.0], [16.7, 17.0], [16.8, 17.0], [16.9, 17.0], [17.0, 17.0], [17.1, 17.0], [17.2, 17.0], [17.3, 17.0], [17.4, 17.0], [17.5, 17.0], [17.6, 17.0], [17.7, 17.0], [17.8, 17.0], [17.9, 17.0], [18.0, 17.0], [18.1, 17.0], [18.2, 17.0], [18.3, 17.0], [18.4, 17.0], [18.5, 17.0], [18.6, 17.0], [18.7, 17.0], [18.8, 17.0], [18.9, 17.0], [19.0, 17.0], [19.1, 17.0], [19.2, 17.0], [19.3, 17.0], [19.4, 17.0], [19.5, 17.0], [19.6, 17.0], [19.7, 17.0], [19.8, 18.0], [19.9, 18.0], [20.0, 18.0], [20.1, 18.0], [20.2, 18.0], [20.3, 18.0], [20.4, 18.0], [20.5, 18.0], [20.6, 18.0], [20.7, 18.0], [20.8, 18.0], [20.9, 18.0], [21.0, 18.0], [21.1, 18.0], [21.2, 18.0], [21.3, 18.0], [21.4, 18.0], [21.5, 18.0], [21.6, 18.0], [21.7, 18.0], [21.8, 18.0], [21.9, 18.0], [22.0, 18.0], [22.1, 18.0], [22.2, 18.0], [22.3, 18.0], [22.4, 18.0], [22.5, 18.0], [22.6, 18.0], [22.7, 18.0], [22.8, 18.0], [22.9, 18.0], [23.0, 18.0], [23.1, 18.0], [23.2, 18.0], [23.3, 18.0], [23.4, 18.0], [23.5, 19.0], [23.6, 19.0], [23.7, 19.0], [23.8, 19.0], [23.9, 19.0], [24.0, 19.0], [24.1, 19.0], [24.2, 19.0], [24.3, 19.0], [24.4, 19.0], [24.5, 19.0], [24.6, 19.0], [24.7, 19.0], [24.8, 19.0], [24.9, 19.0], [25.0, 19.0], [25.1, 19.0], [25.2, 19.0], [25.3, 19.0], [25.4, 19.0], [25.5, 19.0], [25.6, 19.0], [25.7, 19.0], [25.8, 19.0], [25.9, 19.0], [26.0, 19.0], [26.1, 19.0], [26.2, 19.0], [26.3, 19.0], [26.4, 20.0], [26.5, 20.0], [26.6, 20.0], [26.7, 20.0], [26.8, 20.0], [26.9, 20.0], [27.0, 20.0], [27.1, 20.0], [27.2, 20.0], [27.3, 20.0], [27.4, 20.0], [27.5, 20.0], [27.6, 20.0], [27.7, 20.0], [27.8, 20.0], [27.9, 20.0], [28.0, 20.0], [28.1, 20.0], [28.2, 20.0], [28.3, 20.0], [28.4, 20.0], [28.5, 20.0], [28.6, 20.0], [28.7, 20.0], [28.8, 21.0], [28.9, 21.0], [29.0, 21.0], [29.1, 21.0], [29.2, 21.0], [29.3, 21.0], [29.4, 21.0], [29.5, 21.0], [29.6, 21.0], [29.7, 21.0], [29.8, 21.0], [29.9, 21.0], [30.0, 21.0], [30.1, 21.0], [30.2, 21.0], [30.3, 21.0], [30.4, 21.0], [30.5, 21.0], [30.6, 21.0], [30.7, 21.0], [30.8, 21.0], [30.9, 21.0], [31.0, 21.0], [31.1, 21.0], [31.2, 22.0], [31.3, 22.0], [31.4, 22.0], [31.5, 22.0], [31.6, 22.0], [31.7, 22.0], [31.8, 22.0], [31.9, 22.0], [32.0, 22.0], [32.1, 22.0], [32.2, 22.0], [32.3, 22.0], [32.4, 22.0], [32.5, 22.0], [32.6, 22.0], [32.7, 22.0], [32.8, 22.0], [32.9, 22.0], [33.0, 22.0], [33.1, 22.0], [33.2, 23.0], [33.3, 23.0], [33.4, 23.0], [33.5, 23.0], [33.6, 23.0], [33.7, 23.0], [33.8, 23.0], [33.9, 23.0], [34.0, 23.0], [34.1, 23.0], [34.2, 23.0], [34.3, 23.0], [34.4, 23.0], [34.5, 23.0], [34.6, 23.0], [34.7, 23.0], [34.8, 24.0], [34.9, 24.0], [35.0, 24.0], [35.1, 24.0], [35.2, 24.0], [35.3, 24.0], [35.4, 24.0], [35.5, 24.0], [35.6, 24.0], [35.7, 24.0], [35.8, 24.0], [35.9, 24.0], [36.0, 24.0], [36.1, 24.0], [36.2, 24.0], [36.3, 25.0], [36.4, 25.0], [36.5, 25.0], [36.6, 25.0], [36.7, 25.0], [36.8, 25.0], [36.9, 25.0], [37.0, 25.0], [37.1, 25.0], [37.2, 25.0], [37.3, 25.0], [37.4, 25.0], [37.5, 25.0], [37.6, 26.0], [37.7, 26.0], [37.8, 26.0], [37.9, 26.0], [38.0, 26.0], [38.1, 26.0], [38.2, 26.0], [38.3, 26.0], [38.4, 26.0], [38.5, 26.0], [38.6, 26.0], [38.7, 26.0], [38.8, 26.0], [38.9, 27.0], [39.0, 27.0], [39.1, 27.0], [39.2, 27.0], [39.3, 27.0], [39.4, 27.0], [39.5, 27.0], [39.6, 27.0], [39.7, 27.0], [39.8, 27.0], [39.9, 28.0], [40.0, 28.0], [40.1, 28.0], [40.2, 28.0], [40.3, 28.0], [40.4, 28.0], [40.5, 28.0], [40.6, 28.0], [40.7, 29.0], [40.8, 29.0], [40.9, 29.0], [41.0, 29.0], [41.1, 29.0], [41.2, 29.0], [41.3, 29.0], [41.4, 29.0], [41.5, 30.0], [41.6, 30.0], [41.7, 30.0], [41.8, 30.0], [41.9, 30.0], [42.0, 30.0], [42.1, 30.0], [42.2, 31.0], [42.3, 31.0], [42.4, 31.0], [42.5, 31.0], [42.6, 31.0], [42.7, 32.0], [42.8, 32.0], [42.9, 32.0], [43.0, 32.0], [43.1, 32.0], [43.2, 33.0], [43.3, 33.0], [43.4, 33.0], [43.5, 33.0], [43.6, 33.0], [43.7, 34.0], [43.8, 34.0], [43.9, 34.0], [44.0, 34.0], [44.1, 35.0], [44.2, 35.0], [44.3, 35.0], [44.4, 35.0], [44.5, 36.0], [44.6, 36.0], [44.7, 36.0], [44.8, 37.0], [44.9, 37.0], [45.0, 37.0], [45.1, 38.0], [45.2, 38.0], [45.3, 39.0], [45.4, 39.0], [45.5, 39.0], [45.6, 40.0], [45.7, 40.0], [45.8, 41.0], [45.9, 41.0], [46.0, 42.0], [46.1, 42.0], [46.2, 43.0], [46.3, 43.0], [46.4, 44.0], [46.5, 44.0], [46.6, 45.0], [46.7, 45.0], [46.8, 46.0], [46.9, 47.0], [47.0, 48.0], [47.1, 49.0], [47.2, 50.0], [47.3, 51.0], [47.4, 52.0], [47.5, 53.0], [47.6, 54.0], [47.7, 55.0], [47.8, 57.0], [47.9, 59.0], [48.0, 61.0], [48.1, 63.0], [48.2, 64.0], [48.3, 67.0], [48.4, 70.0], [48.5, 72.0], [48.6, 74.0], [48.7, 77.0], [48.8, 79.0], [48.9, 81.0], [49.0, 84.0], [49.1, 88.0], [49.2, 92.0], [49.3, 94.0], [49.4, 97.0], [49.5, 99.0], [49.6, 104.0], [49.7, 108.0], [49.8, 111.0], [49.9, 113.0], [50.0, 115.0], [50.1, 119.0], [50.2, 123.0], [50.3, 128.0], [50.4, 132.0], [50.5, 134.0], [50.6, 135.0], [50.7, 137.0], [50.8, 139.0], [50.9, 141.0], [51.0, 143.0], [51.1, 145.0], [51.2, 146.0], [51.3, 149.0], [51.4, 151.0], [51.5, 153.0], [51.6, 154.0], [51.7, 156.0], [51.8, 159.0], [51.9, 162.0], [52.0, 166.0], [52.1, 172.0], [52.2, 174.0], [52.3, 178.0], [52.4, 181.0], [52.5, 184.0], [52.6, 187.0], [52.7, 192.0], [52.8, 197.0], [52.9, 202.0], [53.0, 207.0], [53.1, 211.0], [53.2, 213.0], [53.3, 216.0], [53.4, 219.0], [53.5, 222.0], [53.6, 224.0], [53.7, 226.0], [53.8, 228.0], [53.9, 230.0], [54.0, 233.0], [54.1, 234.0], [54.2, 235.0], [54.3, 237.0], [54.4, 239.0], [54.5, 240.0], [54.6, 242.0], [54.7, 243.0], [54.8, 246.0], [54.9, 247.0], [55.0, 249.0], [55.1, 251.0], [55.2, 252.0], [55.3, 254.0], [55.4, 256.0], [55.5, 259.0], [55.6, 262.0], [55.7, 266.0], [55.8, 270.0], [55.9, 273.0], [56.0, 275.0], [56.1, 278.0], [56.2, 280.0], [56.3, 282.0], [56.4, 284.0], [56.5, 285.0], [56.6, 287.0], [56.7, 288.0], [56.8, 289.0], [56.9, 290.0], [57.0, 291.0], [57.1, 292.0], [57.2, 293.0], [57.3, 294.0], [57.4, 295.0], [57.5, 296.0], [57.6, 296.0], [57.7, 297.0], [57.8, 298.0], [57.9, 298.0], [58.0, 299.0], [58.1, 299.0], [58.2, 300.0], [58.3, 300.0], [58.4, 301.0], [58.5, 301.0], [58.6, 302.0], [58.7, 302.0], [58.8, 303.0], [58.9, 303.0], [59.0, 304.0], [59.1, 304.0], [59.2, 304.0], [59.3, 305.0], [59.4, 305.0], [59.5, 306.0], [59.6, 306.0], [59.7, 307.0], [59.8, 307.0], [59.9, 307.0], [60.0, 308.0], [60.1, 308.0], [60.2, 309.0], [60.3, 309.0], [60.4, 309.0], [60.5, 310.0], [60.6, 310.0], [60.7, 310.0], [60.8, 311.0], [60.9, 311.0], [61.0, 312.0], [61.1, 312.0], [61.2, 312.0], [61.3, 313.0], [61.4, 313.0], [61.5, 313.0], [61.6, 314.0], [61.7, 314.0], [61.8, 315.0], [61.9, 315.0], [62.0, 315.0], [62.1, 316.0], [62.2, 316.0], [62.3, 317.0], [62.4, 317.0], [62.5, 317.0], [62.6, 318.0], [62.7, 318.0], [62.8, 318.0], [62.9, 319.0], [63.0, 319.0], [63.1, 319.0], [63.2, 319.0], [63.3, 320.0], [63.4, 320.0], [63.5, 321.0], [63.6, 321.0], [63.7, 322.0], [63.8, 322.0], [63.9, 322.0], [64.0, 323.0], [64.1, 323.0], [64.2, 324.0], [64.3, 324.0], [64.4, 324.0], [64.5, 325.0], [64.6, 325.0], [64.7, 326.0], [64.8, 326.0], [64.9, 326.0], [65.0, 327.0], [65.1, 327.0], [65.2, 327.0], [65.3, 328.0], [65.4, 328.0], [65.5, 329.0], [65.6, 329.0], [65.7, 330.0], [65.8, 330.0], [65.9, 330.0], [66.0, 331.0], [66.1, 331.0], [66.2, 332.0], [66.3, 333.0], [66.4, 333.0], [66.5, 333.0], [66.6, 334.0], [66.7, 334.0], [66.8, 335.0], [66.9, 335.0], [67.0, 336.0], [67.1, 336.0], [67.2, 337.0], [67.3, 337.0], [67.4, 338.0], [67.5, 338.0], [67.6, 339.0], [67.7, 339.0], [67.8, 339.0], [67.9, 340.0], [68.0, 341.0], [68.1, 341.0], [68.2, 341.0], [68.3, 342.0], [68.4, 342.0], [68.5, 343.0], [68.6, 343.0], [68.7, 344.0], [68.8, 344.0], [68.9, 345.0], [69.0, 345.0], [69.1, 346.0], [69.2, 346.0], [69.3, 346.0], [69.4, 347.0], [69.5, 347.0], [69.6, 347.0], [69.7, 348.0], [69.8, 348.0], [69.9, 349.0], [70.0, 349.0], [70.1, 350.0], [70.2, 351.0], [70.3, 351.0], [70.4, 352.0], [70.5, 353.0], [70.6, 353.0], [70.7, 354.0], [70.8, 355.0], [70.9, 356.0], [71.0, 356.0], [71.1, 357.0], [71.2, 358.0], [71.3, 358.0], [71.4, 359.0], [71.5, 360.0], [71.6, 361.0], [71.7, 361.0], [71.8, 362.0], [71.9, 363.0], [72.0, 363.0], [72.1, 364.0], [72.2, 365.0], [72.3, 366.0], [72.4, 367.0], [72.5, 368.0], [72.6, 369.0], [72.7, 369.0], [72.8, 370.0], [72.9, 371.0], [73.0, 372.0], [73.1, 374.0], [73.2, 375.0], [73.3, 377.0], [73.4, 378.0], [73.5, 380.0], [73.6, 381.0], [73.7, 382.0], [73.8, 384.0], [73.9, 385.0], [74.0, 386.0], [74.1, 388.0], [74.2, 390.0], [74.3, 391.0], [74.4, 393.0], [74.5, 394.0], [74.6, 396.0], [74.7, 397.0], [74.8, 400.0], [74.9, 401.0], [75.0, 403.0], [75.1, 405.0], [75.2, 407.0], [75.3, 409.0], [75.4, 411.0], [75.5, 415.0], [75.6, 417.0], [75.7, 418.0], [75.8, 420.0], [75.9, 421.0], [76.0, 423.0], [76.1, 425.0], [76.2, 427.0], [76.3, 430.0], [76.4, 432.0], [76.5, 434.0], [76.6, 436.0], [76.7, 439.0], [76.8, 441.0], [76.9, 444.0], [77.0, 446.0], [77.1, 450.0], [77.2, 452.0], [77.3, 454.0], [77.4, 458.0], [77.5, 460.0], [77.6, 461.0], [77.7, 464.0], [77.8, 467.0], [77.9, 470.0], [78.0, 473.0], [78.1, 475.0], [78.2, 478.0], [78.3, 482.0], [78.4, 485.0], [78.5, 489.0], [78.6, 493.0], [78.7, 498.0], [78.8, 503.0], [78.9, 508.0], [79.0, 514.0], [79.1, 519.0], [79.2, 525.0], [79.3, 530.0], [79.4, 539.0], [79.5, 547.0], [79.6, 555.0], [79.7, 564.0], [79.8, 570.0], [79.9, 574.0], [80.0, 578.0], [80.1, 581.0], [80.2, 583.0], [80.3, 585.0], [80.4, 586.0], [80.5, 588.0], [80.6, 590.0], [80.7, 592.0], [80.8, 594.0], [80.9, 596.0], [81.0, 597.0], [81.1, 598.0], [81.2, 599.0], [81.3, 600.0], [81.4, 602.0], [81.5, 603.0], [81.6, 605.0], [81.7, 606.0], [81.8, 608.0], [81.9, 610.0], [82.0, 611.0], [82.1, 613.0], [82.2, 615.0], [82.3, 617.0], [82.4, 619.0], [82.5, 621.0], [82.6, 624.0], [82.7, 627.0], [82.8, 630.0], [82.9, 632.0], [83.0, 634.0], [83.1, 637.0], [83.2, 639.0], [83.3, 641.0], [83.4, 642.0], [83.5, 643.0], [83.6, 644.0], [83.7, 645.0], [83.8, 646.0], [83.9, 646.0], [84.0, 648.0], [84.1, 649.0], [84.2, 651.0], [84.3, 652.0], [84.4, 654.0], [84.5, 655.0], [84.6, 657.0], [84.7, 659.0], [84.8, 660.0], [84.9, 661.0], [85.0, 663.0], [85.1, 664.0], [85.2, 666.0], [85.3, 668.0], [85.4, 669.0], [85.5, 671.0], [85.6, 674.0], [85.7, 676.0], [85.8, 679.0], [85.9, 682.0], [86.0, 684.0], [86.1, 688.0], [86.2, 690.0], [86.3, 693.0], [86.4, 697.0], [86.5, 701.0], [86.6, 705.0], [86.7, 708.0], [86.8, 711.0], [86.9, 715.0], [87.0, 721.0], [87.1, 725.0], [87.2, 727.0], [87.3, 730.0], [87.4, 734.0], [87.5, 739.0], [87.6, 743.0], [87.7, 748.0], [87.8, 753.0], [87.9, 759.0], [88.0, 762.0], [88.1, 769.0], [88.2, 775.0], [88.3, 779.0], [88.4, 791.0], [88.5, 800.0], [88.6, 814.0], [88.7, 825.0], [88.8, 836.0], [88.9, 845.0], [89.0, 852.0], [89.1, 858.0], [89.2, 868.0], [89.3, 872.0], [89.4, 876.0], [89.5, 879.0], [89.6, 882.0], [89.7, 885.0], [89.8, 888.0], [89.9, 894.0], [90.0, 897.0], [90.1, 902.0], [90.2, 906.0], [90.3, 909.0], [90.4, 912.0], [90.5, 916.0], [90.6, 919.0], [90.7, 922.0], [90.8, 928.0], [90.9, 937.0], [91.0, 945.0], [91.1, 952.0], [91.2, 957.0], [91.3, 961.0], [91.4, 964.0], [91.5, 968.0], [91.6, 973.0], [91.7, 976.0], [91.8, 981.0], [91.9, 987.0], [92.0, 993.0], [92.1, 999.0], [92.2, 1006.0], [92.3, 1014.0], [92.4, 1022.0], [92.5, 1029.0], [92.6, 1039.0], [92.7, 1047.0], [92.8, 1055.0], [92.9, 1061.0], [93.0, 1070.0], [93.1, 1077.0], [93.2, 1088.0], [93.3, 1095.0], [93.4, 1110.0], [93.5, 1127.0], [93.6, 1148.0], [93.7, 1157.0], [93.8, 1166.0], [93.9, 1172.0], [94.0, 1176.0], [94.1, 1185.0], [94.2, 1192.0], [94.3, 1202.0], [94.4, 1212.0], [94.5, 1222.0], [94.6, 1235.0], [94.7, 1241.0], [94.8, 1254.0], [94.9, 1265.0], [95.0, 1278.0], [95.1, 1294.0], [95.2, 1304.0], [95.3, 1321.0], [95.4, 1342.0], [95.5, 1357.0], [95.6, 1370.0], [95.7, 1385.0], [95.8, 1404.0], [95.9, 1421.0], [96.0, 1439.0], [96.1, 1456.0], [96.2, 1471.0], [96.3, 1487.0], [96.4, 1500.0], [96.5, 1520.0], [96.6, 1541.0], [96.7, 1563.0], [96.8, 1577.0], [96.9, 1587.0], [97.0, 1598.0], [97.1, 1621.0], [97.2, 1674.0], [97.3, 1698.0], [97.4, 1725.0], [97.5, 1749.0], [97.6, 1766.0], [97.7, 1795.0], [97.8, 1828.0], [97.9, 1868.0], [98.0, 1897.0], [98.1, 1924.0], [98.2, 1958.0], [98.3, 1998.0], [98.4, 2043.0], [98.5, 2085.0], [98.6, 2129.0], [98.7, 2166.0], [98.8, 2197.0], [98.9, 2245.0], [99.0, 2273.0], [99.1, 2353.0], [99.2, 2443.0], [99.3, 2504.0], [99.4, 2588.0], [99.5, 2734.0], [99.6, 2858.0], [99.7, 3061.0], [99.8, 3268.0], [99.9, 3873.0]], "isOverall": false, "label": "POST /api/transactions", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 100.0, "title": "Response Time Percentiles"}},
        getOptions: function() {
            return {
                series: {
                    points: { show: false }
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentiles'
                },
                xaxis: {
                    tickDecimals: 1,
                    axisLabel: "Percentiles",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Percentile value in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : %x.2 percentile was %y ms"
                },
                selection: { mode: "xy" },
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentiles"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesPercentiles"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesPercentiles"), dataset, prepareOverviewOptions(options));
        }
};

/**
 * @param elementId Id of element where we display message
 */
function setEmptyGraph(elementId) {
    $(function() {
        $(elementId).text("No graph series with filter="+seriesFilter);
    });
}

// Response times percentiles
function refreshResponseTimePercentiles() {
    var infos = responseTimePercentilesInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimePercentiles");
        return;
    }
    if (isGraph($("#flotResponseTimesPercentiles"))){
        infos.createGraph();
    } else {
        var choiceContainer = $("#choicesResponseTimePercentiles");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesPercentiles", "#overviewResponseTimesPercentiles");
        $('#bodyResponseTimePercentiles .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimeDistributionInfos = {
        data: {"result": {"minY": 1.0, "minX": 0.0, "maxY": 9900.0, "series": [{"data": [[0.0, 9900.0], [600.0, 1041.0], [700.0, 406.0], [800.0, 311.0], [900.0, 415.0], [1000.0, 244.0], [1100.0, 190.0], [1200.0, 177.0], [1300.0, 122.0], [1400.0, 124.0], [1500.0, 123.0], [1600.0, 63.0], [1700.0, 77.0], [1800.0, 62.0], [1900.0, 57.0], [2000.0, 48.0], [2100.0, 55.0], [2200.0, 42.0], [2300.0, 29.0], [2400.0, 25.0], [2500.0, 24.0], [2600.0, 14.0], [2800.0, 20.0], [2700.0, 11.0], [2900.0, 11.0], [3000.0, 6.0], [3100.0, 8.0], [3300.0, 7.0], [3200.0, 10.0], [3400.0, 2.0], [3500.0, 6.0], [3600.0, 2.0], [3800.0, 2.0], [4000.0, 4.0], [4200.0, 1.0], [4100.0, 3.0], [4300.0, 1.0], [4600.0, 1.0], [4500.0, 1.0], [4700.0, 1.0], [5100.0, 2.0], [5000.0, 1.0], [5300.0, 1.0], [5700.0, 1.0], [6500.0, 1.0], [100.0, 669.0], [200.0, 1064.0], [300.0, 3322.0], [400.0, 789.0], [500.0, 504.0]], "isOverall": false, "label": "POST /api/transactions", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 100, "maxX": 6500.0, "title": "Response Time Distribution"}},
        getOptions: function() {
            var granularity = this.data.result.granularity;
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    barWidth: this.data.result.granularity
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " responses for " + label + " were between " + xval + " and " + (xval + granularity) + " ms";
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimeDistribution"), prepareData(data.result.series, $("#choicesResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshResponseTimeDistribution() {
    var infos = responseTimeDistributionInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeDistribution");
        return;
    }
    if (isGraph($("#flotResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var syntheticResponseTimeDistributionInfos = {
        data: {"result": {"minY": 720.0, "minX": 0.0, "ticks": [[0, "Requests having \nresponse time <= 500ms"], [1, "Requests having \nresponse time > 500ms and <= 1,500ms"], [2, "Requests having \nresponse time > 1,500ms"], [3, "Requests in error"]], "maxY": 15749.0, "series": [{"data": [[0.0, 15749.0]], "color": "#9ACD32", "isOverall": false, "label": "Requests having \nresponse time <= 500ms", "isController": false}, {"data": [[1.0, 3531.0]], "color": "yellow", "isOverall": false, "label": "Requests having \nresponse time > 500ms and <= 1,500ms", "isController": false}, {"data": [[2.0, 720.0]], "color": "orange", "isOverall": false, "label": "Requests having \nresponse time > 1,500ms", "isController": false}, {"data": [], "color": "#FF6347", "isOverall": false, "label": "Requests in error", "isController": false}], "supportsControllersDiscrimination": false, "maxX": 2.0, "title": "Synthetic Response Times Distribution"}},
        getOptions: function() {
            return {
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendSyntheticResponseTimeDistribution'
                },
                xaxis:{
                    axisLabel: "Response times ranges",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                    tickLength:0,
                    min:-0.5,
                    max:3.5
                },
                yaxis: {
                    axisLabel: "Number of responses",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                bars : {
                    show: true,
                    align: "center",
                    barWidth: 0.25,
                    fill:.75
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: function(label, xval, yval, flotItem){
                        return yval + " " + label;
                    }
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var options = this.getOptions();
            prepareOptions(options, data);
            options.xaxis.ticks = data.result.ticks;
            $.plot($("#flotSyntheticResponseTimeDistribution"), prepareData(data.result.series, $("#choicesSyntheticResponseTimeDistribution")), options);
        }

};

// Response time distribution
function refreshSyntheticResponseTimeDistribution() {
    var infos = syntheticResponseTimeDistributionInfos;
    prepareSeries(infos.data, true);
    if (isGraph($("#flotSyntheticResponseTimeDistribution"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        $('#footerSyntheticResponseTimeDistribution .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var activeThreadsOverTimeInfos = {
        data: {"result": {"minY": 71.40912795436016, "minX": 1.7787477E12, "maxY": 179.7490012251639, "series": [{"data": [[1.7787477E12, 71.40912795436016], [1.77874776E12, 179.7490012251639]], "isOverall": false, "label": "users", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77874776E12, "title": "Active Threads Over Time"}},
        getOptions: function() {
            return {
                series: {
                    stack: true,
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 6,
                    show: true,
                    container: '#legendActiveThreadsOverTime'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                selection: {
                    mode: 'xy'
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : At %x there were %y active threads"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesActiveThreadsOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotActiveThreadsOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewActiveThreadsOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Active Threads Over Time
function refreshActiveThreadsOverTime(fixTimestamps) {
    var infos = activeThreadsOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotActiveThreadsOverTime"))) {
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesActiveThreadsOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotActiveThreadsOverTime", "#overviewActiveThreadsOverTime");
        $('#footerActiveThreadsOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var timeVsThreadsInfos = {
        data: {"result": {"minY": 6.0, "minX": 1.0, "maxY": 534.8000000000001, "series": [{"data": [[2.0, 6.0625], [3.0, 7.142857142857143], [4.0, 9.4], [5.0, 11.8], [6.0, 11.24], [7.0, 12.000000000000002], [8.0, 13.0], [9.0, 16.0], [10.0, 16.42857142857143], [11.0, 24.869565217391308], [12.0, 43.5], [13.0, 40.49999999999999], [14.0, 30.333333333333332], [15.0, 35.61538461538462], [16.0, 18.0], [17.0, 54.58333333333333], [18.0, 32.5], [19.0, 45.974358974358964], [20.0, 36.0], [21.0, 53.13793103448276], [22.0, 51.125], [23.0, 53.0], [24.0, 52.444444444444436], [25.0, 64.95833333333334], [26.0, 55.99999999999999], [27.0, 72.76923076923076], [28.0, 70.34615384615383], [29.0, 78.375], [30.0, 53.5], [31.0, 84.36363636363636], [32.0, 136.41666666666666], [33.0, 121.83333333333334], [34.0, 79.00000000000001], [35.0, 29.099999999999998], [36.0, 95.92307692307693], [37.0, 87.51219512195122], [38.0, 67.3], [39.0, 89.61538461538461], [40.0, 91.36363636363637], [41.0, 47.36363636363636], [42.0, 104.9090909090909], [43.0, 77.09090909090911], [44.0, 163.1], [45.0, 72.5], [46.0, 109.33333333333333], [47.0, 94.23404255319153], [48.0, 108.5], [49.0, 50.714285714285715], [50.0, 116.28205128205131], [51.0, 181.875], [53.0, 138.27777777777777], [52.0, 112.2], [55.0, 98.48648648648648], [54.0, 99.0], [56.0, 61.470588235294116], [57.0, 40.61904761904762], [58.0, 79.8181818181818], [59.0, 130.17073170731706], [60.0, 206.73684210526315], [61.0, 119.0], [62.0, 126.07692307692308], [63.0, 123.41666666666666], [64.0, 246.92857142857144], [65.0, 182.42424242424244], [66.0, 53.66666666666667], [67.0, 389.7142857142857], [68.0, 114.94444444444446], [69.0, 197.125], [70.0, 150.1818181818182], [71.0, 103.66129032258065], [72.0, 98.0], [73.0, 113.73076923076923], [74.0, 93.94444444444444], [75.0, 143.41666666666666], [77.0, 146.14634146341461], [78.0, 93.20000000000002], [79.0, 139.0731707317073], [76.0, 173.42500000000004], [80.0, 66.75], [81.0, 118.34210526315789], [82.0, 96.24444444444444], [83.0, 116.33333333333333], [84.0, 117.38297872340422], [85.0, 147.952380952381], [87.0, 162.10526315789474], [86.0, 120.13793103448279], [88.0, 84.76923076923077], [89.0, 144.2758620689655], [90.0, 187.9333333333333], [91.0, 156.348623853211], [92.0, 126.61904761904759], [93.0, 116.69696969696969], [94.0, 152.5428571428572], [95.0, 252.25], [96.0, 117.84210526315789], [97.0, 117.26086956521738], [99.0, 171.33333333333334], [98.0, 167.87499999999997], [101.0, 198.58333333333334], [102.0, 197.03030303030303], [103.0, 207.625], [100.0, 176.0], [104.0, 185.71264367816087], [105.0, 216.84210526315792], [106.0, 109.28571428571428], [107.0, 161.24242424242425], [108.0, 157.6666666666667], [109.0, 131.13333333333335], [110.0, 187.26086956521738], [111.0, 171.3157894736842], [112.0, 201.63043478260863], [113.0, 234.23749999999993], [114.0, 310.4444444444445], [115.0, 134.45098039215685], [116.0, 319.5714285714285], [117.0, 283.92857142857156], [118.0, 217.30434782608697], [119.0, 254.03773584905653], [120.0, 312.9411764705883], [121.0, 315.6956521739131], [122.0, 110.6], [123.0, 145.0909090909091], [124.0, 195.79166666666666], [125.0, 219.12500000000003], [126.0, 261.19047619047615], [127.0, 255.33802816901405], [128.0, 217.52777777777774], [129.0, 161.69565217391306], [130.0, 236.1276595744681], [131.0, 164.25925925925924], [132.0, 313.92105263157896], [133.0, 132.79166666666666], [134.0, 221.96875], [135.0, 228.56164383561645], [136.0, 263.3333333333333], [137.0, 262.9565217391305], [138.0, 268.43999999999994], [139.0, 235.15], [140.0, 255.0757575757576], [141.0, 269.2894736842105], [142.0, 324.43137254901967], [143.0, 265.4285714285714], [144.0, 441.65517241379314], [145.0, 291.50000000000006], [146.0, 270.90476190476187], [147.0, 266.0714285714286], [148.0, 226.98387096774192], [149.0, 364.00000000000006], [150.0, 278.0374999999999], [151.0, 198.0909090909091], [152.0, 210.1304347826087], [154.0, 168.05263157894737], [155.0, 250.44444444444446], [156.0, 289.9569892473118], [157.0, 265.46666666666664], [158.0, 209.39130434782606], [159.0, 183.0], [153.0, 156.07142857142856], [160.0, 224.63829787234047], [161.0, 18.0], [162.0, 388.0389610389609], [163.0, 278.10810810810807], [164.0, 356.7894736842106], [165.0, 371.9523809523811], [166.0, 320.7391304347826], [167.0, 331.6428571428571], [169.0, 59.42857142857143], [170.0, 348.77777777777766], [171.0, 417.8947368421054], [172.0, 454.7674418604651], [173.0, 245.4901960784314], [174.0, 212.61999999999998], [175.0, 384.2142857142857], [168.0, 192.55555555555557], [176.0, 324.24999999999994], [177.0, 231.36686390532546], [178.0, 300.5728155339804], [179.0, 238.54198473282446], [180.0, 313.99999999999994], [181.0, 195.5], [182.0, 255.23039215686265], [183.0, 374.43902439024396], [184.0, 353.70186335403696], [185.0, 271.5866666666667], [186.0, 296.68085106382983], [188.0, 534.8000000000001], [189.0, 500.8695652173912], [190.0, 365.40217391304355], [191.0, 312.53915662650604], [187.0, 248.89285714285714], [192.0, 312.0511463844798], [193.0, 357.48387096774195], [194.0, 288.84210526315786], [195.0, 290.2862068965517], [196.0, 307.20192307692326], [197.0, 254.30927835051534], [198.0, 290.9289940828402], [199.0, 357.22967479674753], [200.0, 402.16691701088956], [1.0, 6.0]], "isOverall": false, "label": "POST /api/transactions", "isController": false}, {"data": [[173.1023500000012, 328.5437500000015]], "isOverall": false, "label": "POST /api/transactions-Aggregated", "isController": false}], "supportsControllersDiscrimination": true, "maxX": 200.0, "title": "Time VS Threads"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    axisLabel: "Number of active threads",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response times in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: { noColumns: 2,show: true, container: '#legendTimeVsThreads' },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s: At %x.2 active threads, Average response time was %y.2 ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesTimeVsThreads"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotTimesVsThreads"), dataset, options);
            // setup overview
            $.plot($("#overviewTimesVsThreads"), dataset, prepareOverviewOptions(options));
        }
};

// Time vs threads
function refreshTimeVsThreads(){
    var infos = timeVsThreadsInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTimeVsThreads");
        return;
    }
    if(isGraph($("#flotTimesVsThreads"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTimeVsThreads");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTimesVsThreads", "#overviewTimesVsThreads");
        $('#footerTimeVsThreads .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var bytesThroughputOverTimeInfos = {
        data : {"result": {"minY": 7177.95, "minX": 1.7787477E12, "maxY": 131723.88333333333, "series": [{"data": [[1.7787477E12, 7177.95], [1.77874776E12, 109822.05]], "isOverall": false, "label": "Bytes received per second", "isController": false}, {"data": [[1.7787477E12, 8609.45], [1.77874776E12, 131723.88333333333]], "isOverall": false, "label": "Bytes sent per second", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77874776E12, "title": "Bytes Throughput Over Time"}},
        getOptions : function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity) ,
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Bytes / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendBytesThroughputOverTime'
                },
                selection: {
                    mode: "xy"
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y"
                }
            };
        },
        createGraph : function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesBytesThroughputOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotBytesThroughputOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewBytesThroughputOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Bytes throughput Over Time
function refreshBytesThroughputOverTime(fixTimestamps) {
    var infos = bytesThroughputOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotBytesThroughputOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesBytesThroughputOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotBytesThroughputOverTime", "#overviewBytesThroughputOverTime");
        $('#footerBytesThroughputOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var responseTimesOverTimeInfos = {
        data: {"result": {"minY": 129.56153219233906, "minX": 1.7787477E12, "maxY": 341.54919298993207, "series": [{"data": [[1.7787477E12, 129.56153219233906], [1.77874776E12, 341.54919298993207]], "isOverall": false, "label": "POST /api/transactions", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77874776E12, "title": "Response Time Over Time"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average response time was %y ms"
                }
            };
        },
        createGraph: function() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Times Over Time
function refreshResponseTimeOverTime(fixTimestamps) {
    var infos = responseTimesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyResponseTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotResponseTimesOverTime"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimesOverTime", "#overviewResponseTimesOverTime");
        $('#footerResponseTimesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var latenciesOverTimeInfos = {
        data: {"result": {"minY": 129.49307253463738, "minX": 1.7787477E12, "maxY": 341.54727534224924, "series": [{"data": [[1.7787477E12, 129.49307253463738], [1.77874776E12, 341.54727534224924]], "isOverall": false, "label": "POST /api/transactions", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77874776E12, "title": "Latencies Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average response latencies in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendLatenciesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average latency was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesLatenciesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotLatenciesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewLatenciesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Latencies Over Time
function refreshLatenciesOverTime(fixTimestamps) {
    var infos = latenciesOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyLatenciesOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotLatenciesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesLatenciesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotLatenciesOverTime", "#overviewLatenciesOverTime");
        $('#footerLatenciesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var connectTimeOverTimeInfos = {
        data: {"result": {"minY": 0.9829542427955024, "minX": 1.7787477E12, "maxY": 2.3048084759576195, "series": [{"data": [[1.7787477E12, 2.3048084759576195], [1.77874776E12, 0.9829542427955024]], "isOverall": false, "label": "POST /api/transactions", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77874776E12, "title": "Connect Time Over Time"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getConnectTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Average Connect Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendConnectTimeOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Average connect time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesConnectTimeOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotConnectTimeOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewConnectTimeOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Connect Time Over Time
function refreshConnectTimeOverTime(fixTimestamps) {
    var infos = connectTimeOverTimeInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyConnectTimeOverTime");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotConnectTimeOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesConnectTimeOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotConnectTimeOverTime", "#overviewConnectTimeOverTime");
        $('#footerConnectTimeOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var responseTimePercentilesOverTimeInfos = {
        data: {"result": {"minY": 5.0, "minX": 1.7787477E12, "maxY": 6576.0, "series": [{"data": [[1.7787477E12, 1467.0], [1.77874776E12, 6576.0]], "isOverall": false, "label": "Max", "isController": false}, {"data": [[1.7787477E12, 376.4000000000001], [1.77874776E12, 918.6000000000022]], "isOverall": false, "label": "90th percentile", "isController": false}, {"data": [[1.7787477E12, 779.6000000000001], [1.77874776E12, 2323.2599999999984]], "isOverall": false, "label": "99th percentile", "isController": false}, {"data": [[1.7787477E12, 458.1999999999998], [1.77874776E12, 1323.5999999999985]], "isOverall": false, "label": "95th percentile", "isController": false}, {"data": [[1.7787477E12, 12.0], [1.77874776E12, 5.0]], "isOverall": false, "label": "Min", "isController": false}, {"data": [[1.7787477E12, 46.0], [1.77874776E12, 149.0]], "isOverall": false, "label": "Median", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77874776E12, "title": "Response Time Percentiles Over Time (successful requests only)"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true,
                        fill: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Response Time in ms",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: '#legendResponseTimePercentilesOverTime'
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s : at %x Response time was %y ms"
                }
            };
        },
        createGraph: function () {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesResponseTimePercentilesOverTime"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotResponseTimePercentilesOverTime"), dataset, options);
            // setup overview
            $.plot($("#overviewResponseTimePercentilesOverTime"), dataset, prepareOverviewOptions(options));
        }
};

// Response Time Percentiles Over Time
function refreshResponseTimePercentilesOverTime(fixTimestamps) {
    var infos = responseTimePercentilesOverTimeInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotResponseTimePercentilesOverTime"))) {
        infos.createGraph();
    }else {
        var choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimePercentilesOverTime", "#overviewResponseTimePercentilesOverTime");
        $('#footerResponseTimePercentilesOverTime .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var responseTimeVsRequestInfos = {
    data: {"result": {"minY": 10.0, "minX": 69.0, "maxY": 402.0, "series": [{"data": [[540.0, 347.0], [565.0, 281.0], [550.0, 342.0], [549.0, 316.0], [546.0, 193.0], [547.0, 313.0], [548.0, 36.0], [571.0, 316.0], [552.0, 34.0], [592.0, 329.5], [603.0, 327.0], [577.0, 319.0], [590.0, 327.0], [588.0, 293.0], [579.0, 214.0], [587.0, 32.0], [631.0, 306.0], [627.0, 304.0], [614.0, 297.0], [638.0, 233.0], [637.0, 154.0], [652.0, 293.0], [640.0, 115.0], [69.0, 10.0], [311.0, 47.0], [332.0, 43.5], [346.0, 41.0], [351.0, 50.0], [367.0, 402.0], [421.0, 34.0], [436.0, 60.5], [444.0, 47.0], [458.0, 44.5], [450.0, 40.5], [464.0, 318.0], [487.0, 216.0], [506.0, 33.0]], "isOverall": false, "label": "Successes", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 652.0, "title": "Response Time Vs Request"}},
    getOptions: function() {
        return {
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Response Time in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: {
                noColumns: 2,
                show: true,
                container: '#legendResponseTimeVsRequest'
            },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median response time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesResponseTimeVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotResponseTimeVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewResponseTimeVsRequest"), dataset, prepareOverviewOptions(options));

    }
};

// Response Time vs Request
function refreshResponseTimeVsRequest() {
    var infos = responseTimeVsRequestInfos;
    prepareSeries(infos.data);
    if (isGraph($("#flotResponseTimeVsRequest"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesResponseTimeVsRequest");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotResponseTimeVsRequest", "#overviewResponseTimeVsRequest");
        $('#footerResponseRimeVsRequest .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};


var latenciesVsRequestInfos = {
    data: {"result": {"minY": 10.0, "minX": 69.0, "maxY": 402.0, "series": [{"data": [[540.0, 347.0], [565.0, 281.0], [550.0, 342.0], [549.0, 316.0], [546.0, 193.0], [547.0, 313.0], [548.0, 36.0], [571.0, 316.0], [552.0, 34.0], [592.0, 329.5], [603.0, 327.0], [577.0, 319.0], [590.0, 327.0], [588.0, 293.0], [579.0, 214.0], [587.0, 32.0], [631.0, 306.0], [627.0, 304.0], [614.0, 297.0], [638.0, 233.0], [637.0, 154.0], [652.0, 293.0], [640.0, 115.0], [69.0, 10.0], [311.0, 47.0], [332.0, 43.5], [346.0, 41.0], [351.0, 50.0], [367.0, 402.0], [421.0, 34.0], [436.0, 60.5], [444.0, 47.0], [458.0, 44.5], [450.0, 40.5], [464.0, 318.0], [487.0, 216.0], [506.0, 33.0]], "isOverall": false, "label": "Successes", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 1000, "maxX": 652.0, "title": "Latencies Vs Request"}},
    getOptions: function() {
        return{
            series: {
                lines: {
                    show: false
                },
                points: {
                    show: true
                }
            },
            xaxis: {
                axisLabel: "Global number of requests per second",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            yaxis: {
                axisLabel: "Median Latency in ms",
                axisLabelUseCanvas: true,
                axisLabelFontSizePixels: 12,
                axisLabelFontFamily: 'Verdana, Arial',
                axisLabelPadding: 20,
            },
            legend: { noColumns: 2,show: true, container: '#legendLatencyVsRequest' },
            selection: {
                mode: 'xy'
            },
            grid: {
                hoverable: true // IMPORTANT! this is needed for tooltip to work
            },
            tooltip: true,
            tooltipOpts: {
                content: "%s : Median Latency time at %x req/s was %y ms"
            },
            colors: ["#9ACD32", "#FF6347"]
        };
    },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesLatencyVsRequest"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotLatenciesVsRequest"), dataset, options);
        // setup overview
        $.plot($("#overviewLatenciesVsRequest"), dataset, prepareOverviewOptions(options));
    }
};

// Latencies vs Request
function refreshLatenciesVsRequest() {
        var infos = latenciesVsRequestInfos;
        prepareSeries(infos.data);
        if(isGraph($("#flotLatenciesVsRequest"))){
            infos.createGraph();
        }else{
            var choiceContainer = $("#choicesLatencyVsRequest");
            createLegend(choiceContainer, infos);
            infos.createGraph();
            setGraphZoomable("#flotLatenciesVsRequest", "#overviewLatenciesVsRequest");
            $('#footerLatenciesVsRequest .legendColorBox > div').each(function(i){
                $(this).clone().prependTo(choiceContainer.find("li").eq(i));
            });
        }
};

var hitsPerSecondInfos = {
        data: {"result": {"minY": 22.533333333333335, "minX": 1.7787477E12, "maxY": 310.8, "series": [{"data": [[1.7787477E12, 22.533333333333335], [1.77874776E12, 310.8]], "isOverall": false, "label": "hitsPerSecond", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77874776E12, "title": "Hits Per Second"}},
        getOptions: function() {
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of hits / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendHitsPerSecond"
                },
                selection: {
                    mode : 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y.2 hits/sec"
                }
            };
        },
        createGraph: function createGraph() {
            var data = this.data;
            var dataset = prepareData(data.result.series, $("#choicesHitsPerSecond"));
            var options = this.getOptions();
            prepareOptions(options, data);
            $.plot($("#flotHitsPerSecond"), dataset, options);
            // setup overview
            $.plot($("#overviewHitsPerSecond"), dataset, prepareOverviewOptions(options));
        }
};

// Hits per second
function refreshHitsPerSecond(fixTimestamps) {
    var infos = hitsPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if (isGraph($("#flotHitsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesHitsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotHitsPerSecond", "#overviewHitsPerSecond");
        $('#footerHitsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
}

var codesPerSecondInfos = {
        data: {"result": {"minY": 20.45, "minX": 1.7787477E12, "maxY": 312.8833333333333, "series": [{"data": [[1.7787477E12, 20.45], [1.77874776E12, 312.8833333333333]], "isOverall": false, "label": "200", "isController": false}], "supportsControllersDiscrimination": false, "granularity": 60000, "maxX": 1.77874776E12, "title": "Codes Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of responses / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendCodesPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "Number of Response Codes %s at %x was %y.2 responses / sec"
                }
            };
        },
    createGraph: function() {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesCodesPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotCodesPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewCodesPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Codes per second
function refreshCodesPerSecond(fixTimestamps) {
    var infos = codesPerSecondInfos;
    prepareSeries(infos.data);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotCodesPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesCodesPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotCodesPerSecond", "#overviewCodesPerSecond");
        $('#footerCodesPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var transactionsPerSecondInfos = {
        data: {"result": {"minY": 20.45, "minX": 1.7787477E12, "maxY": 312.8833333333333, "series": [{"data": [[1.7787477E12, 20.45], [1.77874776E12, 312.8833333333333]], "isOverall": false, "label": "POST /api/transactions-success", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77874776E12, "title": "Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTransactionsPerSecond"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                }
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTransactionsPerSecond"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTransactionsPerSecond"), dataset, options);
        // setup overview
        $.plot($("#overviewTransactionsPerSecond"), dataset, prepareOverviewOptions(options));
    }
};

// Transactions per second
function refreshTransactionsPerSecond(fixTimestamps) {
    var infos = transactionsPerSecondInfos;
    prepareSeries(infos.data);
    if(infos.data.result.series.length == 0) {
        setEmptyGraph("#bodyTransactionsPerSecond");
        return;
    }
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotTransactionsPerSecond"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTransactionsPerSecond");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTransactionsPerSecond", "#overviewTransactionsPerSecond");
        $('#footerTransactionsPerSecond .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

var totalTPSInfos = {
        data: {"result": {"minY": 20.45, "minX": 1.7787477E12, "maxY": 312.8833333333333, "series": [{"data": [[1.7787477E12, 20.45], [1.77874776E12, 312.8833333333333]], "isOverall": false, "label": "Transaction-success", "isController": false}, {"data": [], "isOverall": false, "label": "Transaction-failure", "isController": false}], "supportsControllersDiscrimination": true, "granularity": 60000, "maxX": 1.77874776E12, "title": "Total Transactions Per Second"}},
        getOptions: function(){
            return {
                series: {
                    lines: {
                        show: true
                    },
                    points: {
                        show: true
                    }
                },
                xaxis: {
                    mode: "time",
                    timeformat: getTimeFormat(this.data.result.granularity),
                    axisLabel: getElapsedTimeLabel(this.data.result.granularity),
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20,
                },
                yaxis: {
                    axisLabel: "Number of transactions / sec",
                    axisLabelUseCanvas: true,
                    axisLabelFontSizePixels: 12,
                    axisLabelFontFamily: 'Verdana, Arial',
                    axisLabelPadding: 20
                },
                legend: {
                    noColumns: 2,
                    show: true,
                    container: "#legendTotalTPS"
                },
                selection: {
                    mode: 'xy'
                },
                grid: {
                    hoverable: true // IMPORTANT! this is needed for tooltip to
                                    // work
                },
                tooltip: true,
                tooltipOpts: {
                    content: "%s at %x was %y transactions / sec"
                },
                colors: ["#9ACD32", "#FF6347"]
            };
        },
    createGraph: function () {
        var data = this.data;
        var dataset = prepareData(data.result.series, $("#choicesTotalTPS"));
        var options = this.getOptions();
        prepareOptions(options, data);
        $.plot($("#flotTotalTPS"), dataset, options);
        // setup overview
        $.plot($("#overviewTotalTPS"), dataset, prepareOverviewOptions(options));
    }
};

// Total Transactions per second
function refreshTotalTPS(fixTimestamps) {
    var infos = totalTPSInfos;
    // We want to ignore seriesFilter
    prepareSeries(infos.data, false, true);
    if(fixTimestamps) {
        fixTimeStamps(infos.data.result.series, 10800000);
    }
    if(isGraph($("#flotTotalTPS"))){
        infos.createGraph();
    }else{
        var choiceContainer = $("#choicesTotalTPS");
        createLegend(choiceContainer, infos);
        infos.createGraph();
        setGraphZoomable("#flotTotalTPS", "#overviewTotalTPS");
        $('#footerTotalTPS .legendColorBox > div').each(function(i){
            $(this).clone().prependTo(choiceContainer.find("li").eq(i));
        });
    }
};

// Collapse the graph matching the specified DOM element depending the collapsed
// status
function collapse(elem, collapsed){
    if(collapsed){
        $(elem).parent().find(".fa-chevron-up").removeClass("fa-chevron-up").addClass("fa-chevron-down");
    } else {
        $(elem).parent().find(".fa-chevron-down").removeClass("fa-chevron-down").addClass("fa-chevron-up");
        if (elem.id == "bodyBytesThroughputOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshBytesThroughputOverTime(true);
            }
            document.location.href="#bytesThroughputOverTime";
        } else if (elem.id == "bodyLatenciesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesOverTime(true);
            }
            document.location.href="#latenciesOverTime";
        } else if (elem.id == "bodyCustomGraph") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCustomGraph(true);
            }
            document.location.href="#responseCustomGraph";
        } else if (elem.id == "bodyConnectTimeOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshConnectTimeOverTime(true);
            }
            document.location.href="#connectTimeOverTime";
        } else if (elem.id == "bodyResponseTimePercentilesOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimePercentilesOverTime(true);
            }
            document.location.href="#responseTimePercentilesOverTime";
        } else if (elem.id == "bodyResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeDistribution();
            }
            document.location.href="#responseTimeDistribution" ;
        } else if (elem.id == "bodySyntheticResponseTimeDistribution") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshSyntheticResponseTimeDistribution();
            }
            document.location.href="#syntheticResponseTimeDistribution" ;
        } else if (elem.id == "bodyActiveThreadsOverTime") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshActiveThreadsOverTime(true);
            }
            document.location.href="#activeThreadsOverTime";
        } else if (elem.id == "bodyTimeVsThreads") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTimeVsThreads();
            }
            document.location.href="#timeVsThreads" ;
        } else if (elem.id == "bodyCodesPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshCodesPerSecond(true);
            }
            document.location.href="#codesPerSecond";
        } else if (elem.id == "bodyTransactionsPerSecond") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTransactionsPerSecond(true);
            }
            document.location.href="#transactionsPerSecond";
        } else if (elem.id == "bodyTotalTPS") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshTotalTPS(true);
            }
            document.location.href="#totalTPS";
        } else if (elem.id == "bodyResponseTimeVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshResponseTimeVsRequest();
            }
            document.location.href="#responseTimeVsRequest";
        } else if (elem.id == "bodyLatenciesVsRequest") {
            if (isGraph($(elem).find('.flot-chart-content')) == false) {
                refreshLatenciesVsRequest();
            }
            document.location.href="#latencyVsRequest";
        }
    }
}

/*
 * Activates or deactivates all series of the specified graph (represented by id parameter)
 * depending on checked argument.
 */
function toggleAll(id, checked){
    var placeholder = document.getElementById(id);

    var cases = $(placeholder).find(':checkbox');
    cases.prop('checked', checked);
    $(cases).parent().children().children().toggleClass("legend-disabled", !checked);

    var choiceContainer;
    if ( id == "choicesBytesThroughputOverTime"){
        choiceContainer = $("#choicesBytesThroughputOverTime");
        refreshBytesThroughputOverTime(false);
    } else if(id == "choicesResponseTimesOverTime"){
        choiceContainer = $("#choicesResponseTimesOverTime");
        refreshResponseTimeOverTime(false);
    }else if(id == "choicesResponseCustomGraph"){
        choiceContainer = $("#choicesResponseCustomGraph");
        refreshCustomGraph(false);
    } else if ( id == "choicesLatenciesOverTime"){
        choiceContainer = $("#choicesLatenciesOverTime");
        refreshLatenciesOverTime(false);
    } else if ( id == "choicesConnectTimeOverTime"){
        choiceContainer = $("#choicesConnectTimeOverTime");
        refreshConnectTimeOverTime(false);
    } else if ( id == "choicesResponseTimePercentilesOverTime"){
        choiceContainer = $("#choicesResponseTimePercentilesOverTime");
        refreshResponseTimePercentilesOverTime(false);
    } else if ( id == "choicesResponseTimePercentiles"){
        choiceContainer = $("#choicesResponseTimePercentiles");
        refreshResponseTimePercentiles();
    } else if(id == "choicesActiveThreadsOverTime"){
        choiceContainer = $("#choicesActiveThreadsOverTime");
        refreshActiveThreadsOverTime(false);
    } else if ( id == "choicesTimeVsThreads"){
        choiceContainer = $("#choicesTimeVsThreads");
        refreshTimeVsThreads();
    } else if ( id == "choicesSyntheticResponseTimeDistribution"){
        choiceContainer = $("#choicesSyntheticResponseTimeDistribution");
        refreshSyntheticResponseTimeDistribution();
    } else if ( id == "choicesResponseTimeDistribution"){
        choiceContainer = $("#choicesResponseTimeDistribution");
        refreshResponseTimeDistribution();
    } else if ( id == "choicesHitsPerSecond"){
        choiceContainer = $("#choicesHitsPerSecond");
        refreshHitsPerSecond(false);
    } else if(id == "choicesCodesPerSecond"){
        choiceContainer = $("#choicesCodesPerSecond");
        refreshCodesPerSecond(false);
    } else if ( id == "choicesTransactionsPerSecond"){
        choiceContainer = $("#choicesTransactionsPerSecond");
        refreshTransactionsPerSecond(false);
    } else if ( id == "choicesTotalTPS"){
        choiceContainer = $("#choicesTotalTPS");
        refreshTotalTPS(false);
    } else if ( id == "choicesResponseTimeVsRequest"){
        choiceContainer = $("#choicesResponseTimeVsRequest");
        refreshResponseTimeVsRequest();
    } else if ( id == "choicesLatencyVsRequest"){
        choiceContainer = $("#choicesLatencyVsRequest");
        refreshLatenciesVsRequest();
    }
    var color = checked ? "black" : "#818181";
    if(choiceContainer != null) {
        choiceContainer.find("label").each(function(){
            this.style.color = color;
        });
    }
}

