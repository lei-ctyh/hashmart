const path = require('path');
module.exports = {
    externals: {
    },
    copy: [
        {
        	from: path.join(__dirname, 'src/uni_modules/uni-upgrade-center-app/images'),
        	to: path.join(__dirname, 'dist', process.env.NODE_ENV === 'production' ? 'build' : 'dev', process.env
        		.UNI_PLATFORM, 'uni_modules/uni-upgrade-center-app/images')
        }
    ]
};
