const CopyWebpackPlugin = require('copy-webpack-plugin');
const configExtra = require('./config.extra.js');
const path = require('path');
// const cssAppend = require('./css.append.js');
// const we7Append = require('./we7.append.js');
// const removeOtiose = require('./remove.otiose.js');
// const noPlugins = require('./no.plugins.js');

let copyPlugin = [
	// #ifdef APP-PLUS
	{
		from: path.join(__dirname, 'src/uni_modules/uni-upgrade-center-app/images'),
		to: path.join(__dirname, 'dist', process.env.NODE_ENV === 'production' ? 'build' : 'dev', process.env
			.UNI_PLATFORM, 'uni_modules/uni-upgrade-center-app/images')
	},
	// #endif
]
copyPlugin = copyPlugin.concat(configExtra.copy);
module.exports = {
	transpileDependencies: ['uview-ui'],
	configureWebpack: {
		plugins: [
			new CopyWebpackPlugin(copyPlugin),
			// cssAppend,
			// we7Append,
			// removeOtiose,
			// noPlugins,
		],
		externals: configExtra.externals
	}
}

