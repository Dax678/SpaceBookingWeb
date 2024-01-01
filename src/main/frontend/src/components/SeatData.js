const seatData = [
    //Floor 1
    { id: 1, floorId: 1, name: 'F1_001', position: { top: '700px', left: '200px' }, isSelected: false, isAvailable: true },
    { id: 2, floorId: 1, name: 'F1_002', position: { top: '700px', left: '350px' }, isSelected: false, isAvailable: true },
    { id: 3, floorId: 1, name: 'F1_003', position: { top: '700px', left: '500px' }, isSelected: false, isAvailable: true },

    { id: 4, floorId: 1, name: 'F1_004', position: { top: '700px', left: '730px' }, isSelected: false, isAvailable: true },
    { id: 5, floorId: 1, name: 'F1_005', position: { top: '700px', left: '880px' }, isSelected: false, isAvailable: true },
    { id: 6, floorId: 1, name: 'F1_006', position: { top: '700px', left: '1025px' }, isSelected: false, isAvailable: true },

    { id: 7, floorId: 1, name: 'F1_007', position: { top: '80px', left: '530px' }, isSelected: false, isAvailable: true },
    { id: 8, floorId: 1, name: 'F1_008', position: { top: '80px', left: '630px' }, isSelected: false, isAvailable: true },
    { id: 9, floorId: 1, name: 'F1_009', position: { top: '160px', left: '500px' }, isSelected: false, isAvailable: true },

    { id: 10, floorId: 1, name: 'F1_010', position: { top: '160px', left: '660px' }, isSelected: false, isAvailable: true },
    { id: 11, floorId: 1, name: 'F1_011', position: { top: '230px', left: '540px' }, isSelected: false, isAvailable: true },
    { id: 12, floorId: 1, name: 'F1_012', position: { top: '230px', left: '620px' }, isSelected: false, isAvailable: true },

    // Floor 2
    { id: 13, floorId: 2, name: 'F2_001', position: { top: '60px', left: '270px' }, isSelected: false, isAvailable: true },
    { id: 14, floorId: 2, name: 'F2_002', position: { top: '110px', left: '270px' }, isSelected: false, isAvailable: true },
    { id: 15, floorId: 2, name: 'F2_003', position: { top: '160px', left: '270px' }, isSelected: false, isAvailable: true },

    { id: 16, floorId: 2, name: 'F2_004', position: { top: '60px', left: '370px' }, isSelected: false, isAvailable: true },
    { id: 17, floorId: 2, name: 'F2_005', position: { top: '110px', left: '370px' }, isSelected: false, isAvailable: true },
    { id: 18, floorId: 2, name: 'F2_006', position: { top: '160px', left: '370px' }, isSelected: false, isAvailable: true },

    { id: 19, floorId: 2, name: 'F2_007', position: { top: '60px', left: '440px' }, isSelected: false, isAvailable: true },
    { id: 20, floorId: 2, name: 'F2_008', position: { top: '110px', left: '440px' }, isSelected: false, isAvailable: true },
    { id: 21, floorId: 2, name: 'F2_009', position: { top: '160px', left: '440px' }, isSelected: false, isAvailable: true },

    { id: 22, floorId: 2, name: 'F2_010', position: { top: '60px', left: '540px' }, isSelected: false, isAvailable: true },
    { id: 23, floorId: 2, name: 'F2_011', position: { top: '110px', left: '540px' }, isSelected: false, isAvailable: true },
    { id: 24, floorId: 2, name: 'F2_012', position: { top: '160px', left: '540px' }, isSelected: false, isAvailable: true },

    { id: 25, floorId: 2, name: 'F2_013', position: { top: '60px', left: '620px' }, isSelected: false, isAvailable: true },
    { id: 26, floorId: 2, name: 'F2_014', position: { top: '110px', left: '620px' }, isSelected: false, isAvailable: true },
    { id: 27, floorId: 2, name: 'F2_015', position: { top: '160px', left: '620px' }, isSelected: false, isAvailable: true },

    { id: 28, floorId: 2, name: 'F2_016', position: { top: '60px', left: '720px' }, isSelected: false, isAvailable: true },
    { id: 29, floorId: 2, name: 'F2_017', position: { top: '110px', left: '720px' }, isSelected: false, isAvailable: true },
    { id: 30, floorId: 2, name: 'F2_018', position: { top: '160px', left: '720px' }, isSelected: false, isAvailable: true },

    { id: 31, floorId: 2, name: 'F2_019', position: { top: '610px', left: '270px' }, isSelected: false, isAvailable: true },
    { id: 32, floorId: 2, name: 'F2_020', position: { top: '660px', left: '270px' }, isSelected: false, isAvailable: true },
    { id: 33, floorId: 2, name: 'F2_021', position: { top: '710px', left: '270px' }, isSelected: false, isAvailable: true },

    { id: 34, floorId: 2, name: 'F2_022', position: { top: '610px', left: '370px' }, isSelected: false, isAvailable: true },
    { id: 35, floorId: 2, name: 'F2_023', position: { top: '660px', left: '370px' }, isSelected: false, isAvailable: true },
    { id: 36, floorId: 2, name: 'F2_024', position: { top: '710px', left: '370px' }, isSelected: false, isAvailable: true },

    { id: 37, floorId: 2, name: 'F2_025', position: { top: '610px', left: '440px' }, isSelected: false, isAvailable: true },
    { id: 38, floorId: 2, name: 'F2_026', position: { top: '660px', left: '440px' }, isSelected: false, isAvailable: true },
    { id: 39, floorId: 2, name: 'F2_027', position: { top: '710px', left: '440px' }, isSelected: false, isAvailable: true },

    { id: 40, floorId: 2, name: 'F2_028', position: { top: '610px', left: '540px' }, isSelected: false, isAvailable: true },
    { id: 41, floorId: 2, name: 'F2_029', position: { top: '660px', left: '540px' }, isSelected: false, isAvailable: true },
    { id: 42, floorId: 2, name: 'F2_030', position: { top: '710px', left: '540px' }, isSelected: false, isAvailable: true },

    { id: 43, floorId: 2, name: 'F2_031', position: { top: '610px', left: '620px' }, isSelected: false, isAvailable: true },
    { id: 44, floorId: 2, name: 'F2_032', position: { top: '660px', left: '620px' }, isSelected: false, isAvailable: true },
    { id: 45, floorId: 2, name: 'F2_033', position: { top: '710px', left: '620px' }, isSelected: false, isAvailable: true },

    { id: 46, floorId: 2, name: 'F2_034', position: { top: '610px', left: '720px' }, isSelected: false, isAvailable: true },
    { id: 47, floorId: 2, name: 'F2_035', position: { top: '660px', left: '720px' }, isSelected: false, isAvailable: true },
    { id: 48, floorId: 2, name: 'F2_036', position: { top: '710px', left: '720px' }, isSelected: false, isAvailable: true },

    { id: 49, floorId: 2, name: 'F2_037', position: { top: '110px', left: '1010px' }, isSelected: false, isAvailable: true },
    { id: 50, floorId: 2, name: 'F2_038', position: { top: '650px', left: '1010px' }, isSelected: false, isAvailable: true },
];

export default seatData;
