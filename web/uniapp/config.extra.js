module.exports = process.env.UNI_PLATFORM === 'h5' ? require('./config.extra.h5.js') : require(
	'./config.extra.miniProgram.js');

