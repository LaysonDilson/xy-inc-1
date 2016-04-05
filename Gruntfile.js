module.exports = function (grunt) {
    require('load-grunt-tasks')(grunt);
    
    require('time-grunt')(grunt);

    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),

        cssmin: {
            options: {
                shorthandCompacting: false,
                roundingPrecision: -1,
                keepSpecialComments: '0'
            },
            target: {
                files: {
                	'target/classes/public/css/app.min.css': 'target/classes/public/css/app.css'
                }
            }
        },
        
        less: {
        	all: {
        		src: 'target/classes/public/css/app.less',
        		dest: 'target/classes/public/css/app.css'
        	}
        },

        copy: {
        	all: {
        		files: [{
        			expand: true,
        			cwd: 'src/main/resources/public/css',
        			src: '**/*.css',
        			dest: 'target/classes/public/css'
        		}, {
        			expand: true,
        			cwd: 'src/main/resources/public/css',
        			src: '**/*.less',
        			dest: 'target/classes/public/css'
        		}, {
        			expand: true,
        			cwd: 'src/main/resources/public/js/vendors',
        			src: '**/*.js',
        			dest: 'target/classes/public/js/vendors'
        		}]
        	}
        },

        concat: {
            target: {
                src: ['src/main/resources/public/js/app.module.js', 'src/main/resources/public/js/**/*.js', '!**/*.min.js'],
                dest: 'target/classes/public/js/app.js'
            }
        },

        uglify: {
            options: {
                mangle: {
                    except: ['angular', 'jQuery', 'Backbone']
                }
            },
            dist: {
                src: 'target/classes/public/js/app.js',
                dest: 'target/classes/public/js/app.min.js'
            }
        }
    });

    grunt.registerTask('default', ['copy', 'concat', 'less', 'cssmin', 'uglify']);
};