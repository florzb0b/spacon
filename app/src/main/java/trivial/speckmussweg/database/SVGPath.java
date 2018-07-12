package trivial.speckmussweg.database;

/**
 * Created by Fl0rZ on 12.07.2018.
 */

public interface SVGPath {


    String SCHWEIN_NEU = "M 389.46,34.00\n" +
            "           C 390.36,29.50 392.09,26.20 393.56,22.00\n" +
            "             395.22,17.28 396.11,11.29 389.96,9.58\n" +
            "             385.02,8.21 379.31,11.66 375.00,13.74\n" +
            "             375.00,13.74 348.00,25.43 348.00,25.43\n" +
            "             339.31,28.04 333.80,25.21 324.00,28.00\n" +
            "             320.86,21.99 314.10,19.56 308.04,23.09\n" +
            "             308.04,23.09 300.91,28.59 300.91,28.59\n" +
            "             298.23,29.98 291.08,31.72 288.00,31.91\n" +
            "             288.00,31.91 267.00,30.17 267.00,30.17\n" +
            "             267.00,30.17 236.00,26.54 236.00,26.54\n" +
            "             236.00,26.54 200.00,17.80 200.00,17.80\n" +
            "             185.80,15.06 171.41,14.98 157.00,15.00\n" +
            "             157.00,15.00 145.00,15.91 145.00,15.91\n" +
            "             124.16,17.37 103.71,21.60 86.00,33.36\n" +
            "             70.82,43.43 70.04,48.00 60.00,61.00\n" +
            "             60.79,54.99 62.12,49.30 57.58,44.21\n" +
            "             49.73,35.40 32.44,41.31 33.79,56.00\n" +
            "             34.49,63.61 39.69,67.38 45.00,72.00\n" +
            "             45.00,72.00 35.00,74.25 35.00,74.25\n" +
            "             35.00,74.25 17.00,75.00 17.00,75.00\n" +
            "             18.22,77.11 18.81,78.46 21.11,79.72\n" +
            "             24.36,81.41 28.55,80.35 32.00,79.72\n" +
            "             40.68,78.25 48.18,74.08 57.00,74.00\n" +
            "             55.25,86.96 53.62,89.16 56.45,103.00\n" +
            "             56.45,103.00 62.61,123.00 62.61,123.00\n" +
            "             63.52,134.73 53.94,142.87 52.42,149.00\n" +
            "             49.34,161.37 59.53,175.24 62.32,187.00\n" +
            "             63.83,193.32 64.41,202.73 69.41,207.57\n" +
            "             72.56,210.62 77.86,210.95 82.00,210.90\n" +
            "             82.00,210.90 89.00,210.90 89.00,210.90\n" +
            "             95.28,209.94 97.71,205.96 95.51,200.00\n" +
            "             91.70,189.69 82.31,184.04 86.00,171.00\n" +
            "             86.00,171.00 92.40,184.00 92.40,184.00\n" +
            "             94.14,188.27 96.70,195.57 100.21,198.49\n" +
            "             106.55,203.76 122.24,202.02 130.00,201.00\n" +
            "             129.27,195.61 128.35,189.67 125.44,185.00\n" +
            "             121.94,179.37 115.08,173.74 114.23,167.00\n" +
            "             113.42,160.63 118.58,153.25 125.00,152.24\n" +
            "             127.99,151.77 136.45,153.51 140.00,154.00\n" +
            "             152.22,155.68 164.64,157.98 177.00,158.00\n" +
            "             182.46,158.01 201.75,157.96 205.89,159.74\n" +
            "             210.52,161.73 209.99,166.81 210.00,171.00\n" +
            "             210.00,171.00 210.00,191.00 210.00,191.00\n" +
            "             209.91,198.45 206.86,208.13 208.53,215.00\n" +
            "             210.88,224.67 221.79,229.48 231.00,228.96\n" +
            "             235.11,228.72 238.61,227.38 239.68,222.98\n" +
            "             241.12,217.10 235.84,208.62 236.55,202.00\n" +
            "             236.55,202.00 244.15,176.00 244.15,176.00\n" +
            "             245.39,171.48 249.75,158.60 255.78,159.11\n" +
            "             258.10,159.30 258.82,162.18 259.36,164.00\n" +
            "             259.36,164.00 263.79,182.00 263.79,182.00\n" +
            "             267.17,198.01 263.27,192.09 271.75,209.00\n" +
            "             273.64,212.76 275.35,216.80 279.04,219.15\n" +
            "             282.12,221.10 287.39,221.65 291.00,222.05\n" +
            "             291.00,222.05 298.00,222.70 298.00,222.70\n" +
            "             308.96,221.71 304.69,211.41 302.40,205.00\n" +
            "             300.10,198.59 294.91,194.38 292.64,188.00\n" +
            "             290.27,181.79 291.97,177.24 292.64,171.00\n" +
            "             293.64,163.28 292.20,157.64 298.21,151.09\n" +
            "             300.65,148.43 314.78,137.97 318.00,136.85\n" +
            "             318.00,136.85 335.00,133.00 335.00,133.00\n" +
            "             342.69,130.47 356.66,125.25 364.00,124.10\n" +
            "             364.00,124.10 373.00,124.10 373.00,124.10\n" +
            "             373.00,124.10 381.00,123.04 381.00,123.04\n" +
            "             383.74,122.92 388.50,123.29 390.89,122.26\n" +
            "             399.79,118.44 390.53,107.17 387.00,103.00\n" +
            "             392.18,103.70 402.49,106.81 406.99,105.11\n" +
            "             414.08,102.41 420.30,86.46 414.49,80.43\n" +
            "             411.56,77.39 402.78,77.93 398.00,76.76\n" +
            "             389.02,74.58 382.91,69.70 378.00,62.00\n" +
            "             389.49,56.51 387.37,44.52 389.46,34.00 Z";

    String PIG_NEW = "M75.826,60.857c0.006-0.735,0.259-13.395-1.237-19.103c-0.993-3.79-2.694-7.389-5.594-10.347  c-0.187-0.191-0.384-0.374-0.579-0.556c-1.408-1.322-3.037-2.45-4.878-3.382c-2.964-1.498-6.479-2.491-10.528-2.972  c-0.279-0.033-0.562-0.064-0.848-0.092c-0.271-0.027-0.546-0.053-0.825-0.076c0.116-0.04-0.417-0.08-0.791-0.057  c-0.377-0.023-0.911,0.017-0.794,0.057c-0.278,0.023-0.553,0.048-0.827,0.076c-0.285,0.028-0.566,0.059-0.847,0.092l0.008,0.003  c-3.707,0.441-6.966,1.313-9.763,2.608c-1.712,0.792-3.252,1.745-4.614,2.854c-0.558,0.456-1.09,0.936-1.588,1.445  c-1.927,1.962-3.321,4.213-4.334,6.612c-2.812,6.653-2.504,22.101-2.496,22.796c-0.155,1.451,0.14,2.611,0.848,3.401  c0.563,0.628,1.357,0.976,2.236,0.976c1.576,0,2.934-1.443,3.178-3.365l0.008-0.038l0.018-0.149c0.012-0.141,0.02-0.283,0.02-0.429  c0.007-0.125,0.006-0.259,0.011-0.383c0.013-0.371,0.022-0.753,0.029-1.138c0.018-0.938,0.026-1.898,0.026-2.916  c0.008-5.504,0.025-11.99,2.558-17.938c-0.368,1.389-0.603,2.825-0.692,4.259c-1.176,4.674-1.188,9.473-1.192,13.684  c-0.003,1.342-0.007,2.615-0.044,3.816c1.413,1.853,3.1,3.481,4.998,4.838V91.15c0,2.229,1.808,4.035,4.037,4.035  s4.037-1.808,4.037-4.035V69.094c1.702,0.401,3.474,0.62,5.296,0.62c1.349,0,2.667-0.121,3.953-0.346V91.15  c0,2.229,1.807,4.035,4.036,4.035c2.229,0,4.035-1.808,4.035-4.035V66.33c2.373-1.455,4.461-3.326,6.17-5.512  c-0.045-1.258-0.051-2.609-0.051-4.039c-0.009-5.047-0.022-10.937-2.045-16.445c0.002-0.695-0.037-1.382-0.115-2.059  c2.78,6.098,2.797,12.823,2.805,18.502c0.001,1.104,0.005,2.166,0.025,3.174c0.01,0.375,0.021,0.745,0.034,1.105  c0.003,0.047,0.003,0.094,0.005,0.14c0,0.176,0.01,0.331,0.024,0.485l0.002,0.024l0.013,0.084c0.01,0.077,0.031,0.151,0.045,0.228  c0.307,1.822,1.619,3.176,3.143,3.176c0.879,0,1.672-0.346,2.234-0.975C75.686,63.43,75.979,62.27,75.826,60.857";



    String NEW = "M330.627,226.667c-5.891,0-10.671,4.781-10.671,10.67c0,5.89,4.78,10.663,10.671,10.663\n" +
            "\t\t\tc5.889,0,10.654-4.772,10.654-10.663C341.281,231.447,336.516,226.667,330.627,226.667z" +
            "M507.18,324.457l-31.276-20.855l-49.243-45.971c-3.156-18.2-18.561-96.088-57.117-132.591\n" +
            "\t\t\tc-3.234-3.062-6.624-5.914-10.123-8.593c6.561-7.069,14.686-14.709,24.23-22.739c19.42-16.342,37.994-28.66,38.166-28.785\n" +
            "\t\t\tc3.344-2.203,5.156-6.078,4.734-10.045c-0.438-3.968-3.047-7.366-6.765-8.804c-1.391-0.531-34.229-13.014-75.614-13.873\n" +
            "\t\t\tc-48.525-1.016-88.441,14.506-116.656,45.072c-1.921-0.016-3.953-0.023-6.046-0.023c-27.668,0-69.209,1.594-109.969,9.171\n" +
            "\t\t\tc-49.806,9.272-86.27,25.215-108.392,47.399c-2.343,2.344-3.453,5.648-3.015,8.937L42.76,462.721\n" +
            "\t\t\tc0.703,5.279,5.218,9.248,10.561,9.248c58.57,0,109.672-20.248,126.514-27.699c35.167,23.574,71.99,35.51,109.548,35.51\n" +
            "\t\t\tc40.979,0,75.145-14.467,96.596-26.621c16.873-9.561,28.668-19.121,34.089-23.84h38.542c4.03,0,7.732-2.297,9.529-5.906\n" +
            "\t\t\tl42.666-85.332C513.211,333.27,511.664,327.426,507.18,324.457z M407.273,412.133c-7.984,7.203-54.306,46.322-117.89,46.322\n" +
            "\t\t\tc-34.887,0-69.335-11.826-102.362-35.137c-0.078-0.063-0.172-0.094-0.25-0.156c-0.203-0.125-0.39-0.25-0.593-0.375\n" +
            "\t\t\tc-0.094-0.047-0.188-0.094-0.281-0.156c-0.219-0.109-0.422-0.219-0.641-0.313c-0.063-0.031-0.109-0.063-0.172-0.078\n" +
            "\t\t\tc-0.297-0.125-29.855-13.123-50.509-46.539c-3.093-5-9.67-6.563-14.67-3.469c-5.015,3.109-6.562,9.686-3.468,14.686\n" +
            "\t\t\tc12.842,20.777,28.637,34.807,40.823,43.479c-20.497,7.717-55.586,18.514-94.612,20.043L21.966,145.35\n" +
            "\t\t\tc44.744-39.752,142.777-46.126,191.349-46.72c-0.016,3.39,1.578,6.733,4.562,8.812c4.843,3.367,11.482,2.171,14.842-2.664\n" +
            "\t\t\tc24.278-34.886,61.428-52.142,110.422-51.267c18.997,0.344,36.26,3.46,48.759,6.499c-6.421,4.789-14.014,10.678-21.825,17.24\n" +
            "\t\t\tc-28.371,23.856-45.104,44.471-49.728,61.265c-1.578,5.679,1.765,11.553,7.437,13.115c0.953,0.258,1.906,0.383,2.844,0.383\n" +
            "\t\t\tc4.687,0,8.967-3.101,10.279-7.835c0.906-3.281,2.672-6.983,5.234-11.029c3.046,2.304,5.967,4.765,8.732,7.381\n" +
            "\t\t\tc37.558,35.542,51.165,123.125,51.306,124c0.344,2.381,1.499,4.568,3.265,6.209l46.729,43.611L407.273,412.133z M473.341,365.326\n" +
            "\t\t\th-4.078c-5.89,0-10.654,4.766-10.654,10.656c0,3.779,1.952,7.076,4.921,8.967l-11.514,23.028h-18.81l40.198-80.41l14.107,9.404\n" +
            "\t\t\tL473.341,365.326z";

    String NEWERPIG = "M4.13,243.03c7.581,12.395,22.25,15.205,35.625,15.205c2.65,0,5.239-0.117,7.723-0.283\n" +
            "\t\t\tc-15.63,37.437-24.086,77.505-24.086,115.231c0,49.899,12.265,86.368,37.498,111.489c36.777,36.617,95.508,43.644,165.099,44.783\n" +
            "\t\t\tc1.843,0.857,3.778,1.664,5.825,2.429c21.331,7.944,49.486,9.018,74.219,9.018c24.734,0,52.888-1.073,74.219-9.018\n" +
            "\t\t\tc2.047-0.765,3.982-1.571,5.818-2.429c69.591-1.14,128.322-8.172,165.099-44.789c25.233-25.122,37.498-61.591,37.498-111.489\n" +
            "\t\t\tc0-37.727-8.449-77.789-24.08-115.225c15.015,1.004,34.22-0.105,43.286-14.934c12.406-20.29-5.19-48.235-25.566-80.599\n" +
            "\t\t\tc-11.568-18.373-23.525-37.375-28.247-52.494C548.543,92.243,535.384,79.978,516,74.474c-24.395-6.928-53.202-1.892-73.073,3.55\n" +
            "\t\t\tc-18.613,5.097-36.284,12.542-47.883,19.969c-28.826-9.905-58.769-14.922-89.043-14.922c-30.281,0-60.216,5.017-89.049,14.922\n" +
            "\t\t\tc-11.593-7.427-29.27-14.873-47.883-19.969c-19.871-5.442-48.678-10.478-73.073-3.55c-19.377,5.504-32.536,17.769-38.059,35.458\n" +
            "\t\t\tc-4.715,15.119-16.678,34.121-28.247,52.494C9.32,194.789-8.276,222.74,4.13,243.03z M103.934,102.412\n" +
            "\t\t\tc13.806-3.92,34.755-2.601,57.468,3.624c20.746,5.683,37.695,13.954,43.723,19.187l6.471,5.621l12.216-4.48\n" +
            "\t\t\tc26.946-9.584,53.831-14.244,82.189-14.244c28.352,0,55.236,4.659,82.183,14.244l12.222,4.48l6.472-5.621\n" +
            "\t\t\tc6.028-5.233,22.978-13.504,43.724-19.187c22.718-6.225,43.661-7.544,57.467-3.624c13.012,3.692,16.66,10.996,18.269,16.167\n" +
            "\t\t\tc5.843,18.737,18.829,39.372,31.391,59.323c7.704,12.241,14.982,23.803,19.852,33.738c4.919,10.034,5.399,14.533,5.368,16.05\n" +
            "\t\t\tc-1.497,0.678-6.489,2.219-19.278,1.079c-6.484-0.58-13.135-1.615-19.785-3.069l-0.721-0.161\n" +
            "\t\t\tc-66.466-14.878-116.562-68.672-117.049-69.203l-10.7,9.806l-10.705,9.806c1.824,1.991,37.115,40.043,90.016,63.754\n" +
            "\t\t\tc11.07,4.961,22.903,9.294,35.372,12.536c19.076,38.571,29.535,81.271,29.535,120.945c0,41.782-9.467,71.521-28.949,90.916\n" +
            "\t\t\tc-24.716,24.61-65.715,32.771-115.626,35.372c1.732-4.808,2.657-9.627,3.045-14.207l0.203,0.013\n" +
            "\t\t\tc0.13-2.102,2.774-52.223-32.308-116.883c-7.631-14.059-11.106-27.261-10.423-39.451c7.229,5.836,16.672,9.27,26.965,9.27h0.006\n" +
            "\t\t\tc3.428,0,6.879-0.389,10.256-1.159c22.953-5.227,37.689-26.749,32.852-47.976c-4.05-17.763-20.549-30.17-40.13-30.17\n" +
            "\t\t\tc-3.427,0-6.879,0.388-10.256,1.159c-6.928,1.578-13.104,4.653-18.219,8.746l-0.068-0.093\n" +
            "\t\t\tc-1.491,1.079-14.811,11.082-23.402,30.035c-7.976,17.59-13.251,46.355,6.896,83.49c2.971,5.473,5.628,10.816,8.013,16\n" +
            "\t\t\tc-18.743-12.062-40.32-18.552-62.447-18.552c-22.01,0-43.477,6.422-62.146,18.36c2.724-5.929,5.43-11.235,7.908-15.803\n" +
            "\t\t\tc20.148-37.141,14.872-65.898,6.903-83.49c-8.592-18.952-21.911-28.956-23.402-30.034l-0.068,0.092\n" +
            "\t\t\tc-5.122-4.092-11.292-7.162-18.219-8.746c-3.378-0.771-6.829-1.159-10.256-1.159c-19.581,0-36.086,12.407-40.129,30.169\n" +
            "\t\t\tc-4.833,21.227,9.904,42.744,32.851,47.977c3.377,0.77,6.829,1.158,10.256,1.158c10.299,0,19.748-3.439,26.977-9.275\n" +
            "\t\t\tc0.685,12.185-2.798,25.387-10.435,39.458c-24.111,44.438-30.404,82.016-31.938,101.85c-0.407,3.155-0.61,6.305-0.61,9.448\n" +
            "\t\t\tc0,6.127,0.832,12.968,3.285,19.778c-49.905-2.601-90.904-10.761-115.625-35.371c-19.477-19.396-28.943-49.135-28.943-90.917\n" +
            "\t\t\tc0-39.667,10.453-82.368,29.529-120.944c12.462-3.242,24.302-7.575,35.372-12.536c52.9-23.711,88.192-61.764,90.016-63.754\n" +
            "\t\t\tl-10.693-9.806l-10.712-9.818c-0.499,0.542-50.595,54.33-117.061,69.215l-0.025,0.006v-0.006\n" +
            "\t\t\tc-6.903,1.541-13.806,2.632-20.537,3.229c-12.789,1.14-17.781-0.407-19.279-1.079c-0.031-1.516,0.456-6.015,5.368-16.049\n" +
            "\t\t\tc4.869-9.936,12.148-21.498,19.853-33.738c12.561-19.951,25.547-40.58,31.39-59.323\n" +
            "\t\t\tC87.274,113.408,90.923,106.104,103.934,102.412z M306.026,511.871c-39.945,0-61.499-4.086-72.599-11.347\n" +
            "\t\t\tc-6.928-4.536-9.788-10.305-10.49-17.097c-0.025-0.679-0.129-4.253,0.29-10.151c2.028-14.047,10.478-28.925,23.482-40.888\n" +
            "\t\t\tc16.574-15.255,37.64-23.662,59.317-23.662c21.676,0,42.743,8.401,59.316,23.662c15.002,13.806,23.957,31.488,23.957,47.304\n" +
            "\t\t\tc0,8.425-2.508,15.488-10.675,20.832C367.524,507.785,345.971,511.871,306.026,511.871z M401.706,288.38\n" +
            "\t\t\tc1.27-0.29,2.546-0.438,3.809-0.438c5.85,0,10.817,3.193,11.822,7.581c1.257,5.516-3.778,11.569-10.99,13.215\n" +
            "\t\t\tc-1.263,0.29-2.545,0.432-3.809,0.432l0,0c-5.849,0-10.816-3.187-11.821-7.582C389.46,296.072,394.495,290.02,401.706,288.38z\n" +
            "\t\t\t M221.544,301.588c-0.999,4.395-5.972,7.582-11.821,7.582c-1.257,0-2.539-0.148-3.809-0.432\n" +
            "\t\t\tc-7.211-1.646-12.247-7.692-10.989-13.215c0.999-4.395,5.972-7.581,11.821-7.581c1.257,0,2.54,0.148,3.809,0.438\n" +
            "\t\t\tC217.766,290.02,222.801,296.072,221.544,301.588z\n" +
            "M247.251,490.114c0,0,51.52,2.509,48.192-28.376C292.115,430.859,241.378,439.981,247.251,490.114z\n" +
            "M316.559,461.738c-3.328,30.879,48.191,28.376,48.191,28.376C370.618,439.981,319.881,430.859,316.559,461.738z";


    String PIG = "M22,6.4V2h-4.4c-1.5,0-2.8,0.9-3.4,2.3c-1.5-0.4-3.1-0.4-4.5,0C9.2,2.9,7.9,2,6.4,2H2l0,4.4c0,1.3,0.7,2.4,1.7,3.1   C3.2,10.6,3,11.8,3,13c0,5,4,9,9,9s9-4,9-9c0-1.2-0.2-2.4-0.7-3.5C21.3,8.8,22,7.6,22,6.4z M4.7,7.7C4.3,7.4,4,6.9,4,6.4L4,4h2.4   C7,4,7.6,4.4,7.9,5C6.6,5.7,5.5,6.6,4.7,7.7z M8,11c0-0.6,0.4-1,1-1c0.6,0,1,0.4,1,1c0,0.6-0.4,1-1,1C8.4,12,8,11.5,8,11z M12,20   c-2.2,0-4-1.3-4-3c0-1.7,1.8-3,4-3c2.2,0,4,1.3,4,3C16,18.6,14.2,20,12,20z M15,12c-0.6,0-1-0.4-1-1c0-0.6,0.4-1,1-1   c0.6,0,1,0.4,1,1C16,11.5,15.6,12,15,12z M20,6.4c0,0.5-0.3,1.1-0.7,1.4c-0.8-1.1-1.9-2.1-3.1-2.7c0.2-0.6,0.8-1,1.5-1H20V6.4z";
}
